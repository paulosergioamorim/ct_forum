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

    public User save(CreateUserDto dto) throws EmailAlreadyExists, PasswordsDoesNotMatch {
        if (!Objects.equals(dto.password(), dto.passwordConfirm())) {
            throw new PasswordsDoesNotMatch();
        }

        Optional<User> existingUser = usersRepository.findByEmail(dto.email());

        if (existingUser.isPresent()) {
            throw new EmailAlreadyExists();
        }

        String passwordHash = passwordService.hash(dto.password());
        User user = new User(dto.name(), dto.email(), passwordHash, dto.role());
        return usersRepository.save(user);
    }
}
