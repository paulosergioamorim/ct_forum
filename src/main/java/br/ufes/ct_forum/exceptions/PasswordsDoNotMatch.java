package br.ufes.ct_forum.exceptions;

public class PasswordsDoNotMatch extends RuntimeException {
    public PasswordsDoNotMatch() {
        super("Senhas não coincidem");
    }
}
