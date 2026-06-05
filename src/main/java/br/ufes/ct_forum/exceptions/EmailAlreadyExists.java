package br.ufes.ct_forum.exceptions;

public class EmailAlreadyExists extends RuntimeException {
    public EmailAlreadyExists() {
        super("Email já em uso");
    }
}
