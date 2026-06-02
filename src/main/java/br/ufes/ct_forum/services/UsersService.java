package br.ufes.ct_forum.services;

import br.ufes.ct_forum.dtos.CreateUserDto;
import br.ufes.ct_forum.exception.EmailAlreadyExists;
import br.ufes.ct_forum.exception.PasswordsDoesNotMatch;
import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.repositories.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordService passwordService;

    public UsersService(UsersRepository usersRepository, PasswordService passwordService) {
        this.usersRepository = usersRepository;
        this.passwordService = passwordService;
    }

    public Page<User> findAll(Pageable page) {
        return usersRepository.findAll(page);
    }

    public Optional<User> findById(long id) {
        return usersRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public User save(CreateUserDto dto) throws EmailAlreadyExists, PasswordsDoesNotMatch {
        if (!Objects.equals(dto.password(), dto.passwordConfirm())) throw new PasswordsDoesNotMatch();

        Optional<User> existingUser = usersRepository.findByEmail(dto.email());

        if (existingUser.isPresent()) throw new EmailAlreadyExists();

        String passwordHash = passwordService.hash(dto.password());
        User user = new User(dto.name(), dto.email(), passwordHash, dto.role());
        return usersRepository.save(user);
    }

    public void update(long id, CreateUserDto dto) throws PasswordsDoesNotMatch, EmailAlreadyExists {
        Optional<User> existingUser = usersRepository.findById(id);

        if (existingUser.isEmpty()) return;

        if (!Objects.equals(dto.password(), dto.passwordConfirm())) throw new PasswordsDoesNotMatch();

        Optional<User> existingUserByEmail = usersRepository.findByEmail(dto.email());

        if (existingUserByEmail.isPresent()) throw new EmailAlreadyExists();

        User user = existingUser.get(); // persistent entity

        if (dto.password() != null) {
            String passwordHash = passwordService.hash(dto.password());
            user.setPasswordHash(passwordHash);
        }
        if (dto.name() != null) user.setName(dto.name());
        if (dto.email() != null) user.setEmail(dto.email());
        if (dto.role() != null) user.setRole(dto.role());

        usersRepository.save(user);
    }

    public void delete(long id) {
        usersRepository.deleteById(id);
    }
}
