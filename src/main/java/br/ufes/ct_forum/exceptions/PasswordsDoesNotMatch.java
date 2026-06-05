package br.ufes.ct_forum.exceptions;

public class PasswordsDoesNotMatch extends RuntimeException {
    public PasswordsDoesNotMatch() {
        super("Senhas não coincidem");
    }
}
