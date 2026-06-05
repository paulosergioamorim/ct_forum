package br.ufes.ct_forum.services;

import de.mkammerer.argon2.Argon2;
import org.springframework.stereotype.Service;

import static br.ufes.ct_forum.config.Argon2Config.*;

@Service
public class PasswordService {
    private final Argon2 argon2;

    public PasswordService(Argon2 argon2) {
        this.argon2 = argon2;
    }

    public String hash(String password) {
        return argon2.hash(ITERATIONS, MEMORY_COST, PARALLELISM, password.toCharArray());
    }

    public boolean verify(String passwordHash, String password) {
        return argon2.verify(passwordHash, password.toCharArray());
    }
}
