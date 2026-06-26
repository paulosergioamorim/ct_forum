package br.ufes.ct_forum.services;

import com.password4j.Argon2Function;
import com.password4j.Password;
import com.password4j.types.Argon2;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Argon2PasswordEncoder implements PasswordEncoder {
    private final Argon2Function argon2 = Argon2Function.getInstance(
            65536,  // memory (KB) — 64MB
            3,      // iterations
            2,      // parallelism
            64,     // output length (bits)
            Argon2.ID);

    @Override
    public String encode(@NotNull CharSequence rawPassword) {
        return Password.hash(Objects.requireNonNull(rawPassword).toString()).with(argon2).getResult();
    }

    @Override
    public boolean matches(@NotNull CharSequence rawPassword, @NotNull String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) return false;

        return Password.check(rawPassword.toString(), encodedPassword).with(argon2);
    }
}
