package br.ufes.ct_forum.controllers;

import br.ufes.ct_forum.dtos.ErrorDto;
import br.ufes.ct_forum.exceptions.EmailAlreadyExists;
import br.ufes.ct_forum.exceptions.NotFoundException;
import br.ufes.ct_forum.exceptions.PasswordsDoNotMatch;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Interceptador global de exceções da API.
 * <p>
 * Captura as exceções (RunTimeExceptions) lançadas pelas camadas de serviço e 
 * as converte em respostas HTTP padronizadas utilizando o {@link ErrorDto}.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Trata exceções de regras de negócio que configuram requisições inválidas pelo cliente.
     *
     * @param ex      A exceção capturada pelo framework.
     * @param request A requisição web atual (usada para extrair a URI do erro).
     * @return Uma resposta com status HTTP 400 (Bad Request) encapsulando o erro.
     */
    @ExceptionHandler({EmailAlreadyExists.class, PasswordsDoNotMatch.class})
    public ResponseEntity<ErrorDto> handleBadRequestException(RuntimeException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.of(HttpStatus.BAD_REQUEST, ex.getMessage(), request));
    }

    /**
     * Trata exceções de recursos não encontrados no banco de dados.
     *
     * @param ex      A exceção capturada pelo framework.
     * @param request A requisição web atual.
     * @return Uma resposta com status HTTP 404 (Not Found) encapsulando o erro.
     */
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorDto> handleNotFoundException(RuntimeException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.of(HttpStatus.NOT_FOUND, ex.getMessage(), request));
    }

    /**
     * Tratamento genérico para qualquer outra exceção não mapeada (Catch-all).
     *
     * @param request A requisição web atual.
     * @return Uma resposta com status HTTP 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorDto.of(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", request));
    }
}
