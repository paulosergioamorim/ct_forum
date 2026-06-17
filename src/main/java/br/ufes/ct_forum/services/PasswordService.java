package br.ufes.ct_forum.services;

import de.mkammerer.argon2.Argon2;
import org.springframework.stereotype.Service;

import static br.ufes.ct_forum.config.Argon2Config.*;

/**
 * Serviço responsável pela criptografia e validação de senhas dos usuários.
 * <p>
 * Esta classe centraliza a lógica de hash utilizando o algoritmo <b>Argon2</b>,
 * garantindo que as senhas em texto plano nunca sejam manipuladas diretamente
 * fora deste escopo ou expostas ao banco de dados.
 * </p>
 */
@Service
public class PasswordService {
    private final Argon2 argon2;

    /**
     * Construtor da classe.

     * @param argon2 Instância do codificador Argon2 configurada na aplicação.
     */
    public PasswordService(Argon2 argon2) {
        this.argon2 = argon2;
    }

    /**
     * Gera um hash seguro a partir de uma senha em texto plano.
     * <p>
     * Utiliza as configurações de custo definidas globalmente (iterações, 
     * custo de memória e paralelismo) importadas de {@code Argon2Config}.
     * A senha é convertida para um array de caracteres.
     * </p>
     *
     * @param password A senha em texto plano fornecida pelo usuário.
     * @return O hash resultante codificado em formato String.
     */
    public String hash(String password) {
        return argon2.hash(ITERATIONS, MEMORY_COST, PARALLELISM, password.toCharArray());
    }

    /**
     * Verifica se uma senha em texto plano corresponde a um hash previamente armazenado.
     *
     * @param passwordHash O hash armazenado no banco de dados (ex: entidade User).
     * @param password     A senha em texto plano informada.
     * @return {@code true} se a senha for válida e corresponder ao hash, {@code false} caso contrário.
     */
    public boolean verify(String passwordHash, String password) {
        return argon2.verify(passwordHash, password.toCharArray());
    }
}
