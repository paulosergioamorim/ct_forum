package br.ufes.ct_forum.services;

import br.ufes.ct_forum.dtos.CreateUserDto;
import br.ufes.ct_forum.exceptions.EmailAlreadyExists;
import br.ufes.ct_forum.exceptions.NotFoundException;
import br.ufes.ct_forum.exceptions.PasswordsDoesNotMatch;
import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.repositories.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public User findById(long id) {
        return usersRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário com id " + id + " não encontrado"));
    }

    public User findByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuário com email " + email + " não encontrado"));
    }

    public User save(CreateUserDto dto) {
        if (!Objects.equals(dto.password(), dto.passwordConfirm())) throw new PasswordsDoesNotMatch();

        Optional<User> existingUser = usersRepository.findByEmail(dto.email());

        if (existingUser.isPresent()) throw new EmailAlreadyExists();

        String passwordHash = passwordService.hash(dto.password());
        User user = new User(dto.name(), dto.email(), passwordHash, dto.role());
        return usersRepository.save(user);
    }

    @Transactional
    public void updateById(long id, CreateUserDto dto) {
        User user = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário com id " + id + " não encontrado"));

        if (dto.email() != null) {
            usersRepository.findByEmail(dto.email()).ifPresent(found -> {
                if (found.getId() != id) throw new EmailAlreadyExists();
            });
            user.setEmail(dto.email());
        }

        if (dto.password() != null) {
            if (!Objects.equals(dto.password(), dto.passwordConfirm())) throw new PasswordsDoesNotMatch();
            user.setPasswordHash(passwordService.hash(dto.password()));
        }

        if (dto.name() != null) user.setName(dto.name());
        if (dto.role() != null) user.setRole(dto.role());
    }

    public void deleteById(long id) {
        if (!usersRepository.existsById(id)) throw new NotFoundException("Usuário com id " + id + " não encontrado");

        usersRepository.deleteById(id);
    }
}
