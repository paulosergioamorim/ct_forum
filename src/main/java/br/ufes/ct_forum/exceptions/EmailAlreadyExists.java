package br.ufes.ct_forum.exceptions;

/**
 * Exceção lançada quando há uma tentativa de registrar ou atualizar um usuário
 * utilizando um endereço de e-mail que já está vinculado a outra conta no banco de dados.
 */
public class EmailAlreadyExists extends RuntimeException {
    /**
     * Constrói a exceção injetando a mensagem padrão "Email já em uso" 
     * para facilitar a padronização das respostas de erro na API.
     */
    public EmailAlreadyExists() {
        super("Email já em uso");
    }
}
