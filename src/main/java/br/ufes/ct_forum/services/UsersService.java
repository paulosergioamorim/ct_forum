package br.ufes.ct_forum.services;

import br.ufes.ct_forum.repositories.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordService passwordService;

    public UsersService(UsersRepository usersRepository, PasswordService passwordService) {
        this.usersRepository = usersRepository;
        this.passwordService = passwordService;
    }
}
