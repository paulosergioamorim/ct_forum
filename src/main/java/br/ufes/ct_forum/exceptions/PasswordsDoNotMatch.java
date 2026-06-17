package br.ufes.ct_forum.exceptions;

/**
 * Exceção de negócio lançada quando a senha fornecida e sua respectiva 
 * confirmação não são idênticas.
 */
public class PasswordsDoNotMatch extends RuntimeException {
    /**
     * Constrói a exceção injetando a mensagem padrão "Senhas não coincidem".
     */
    public PasswordsDoNotMatch() {
        super("Senhas não coincidem");
    }
}
