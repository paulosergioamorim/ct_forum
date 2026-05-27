package br.ufes.ct_forum.config;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Argon2Config {
    public static final int ITERATIONS = 12;
    public static final int PARALLELISM = 1;
    public static final int MEMORY_COST = 65536; // 64MB

    @Bean
    public Argon2 createArgon2Instance() {
        return Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }
}
