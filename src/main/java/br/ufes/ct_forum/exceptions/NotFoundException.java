package br.ufes.ct_forum.exceptions;

/**
 * Exceção genérica lançada quando um recurso solicitado não é encontrado.
 */
public class NotFoundException extends RuntimeException {
    /**
     * Constrói a exceção injetando uma mensagem de detalhe customizada.
     *
     * @param message A descrição do recurso não encontrado 
     */
    public NotFoundException(String message) {
        super(message);
    }
}
