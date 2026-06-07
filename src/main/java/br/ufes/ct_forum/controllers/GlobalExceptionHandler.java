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

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({EmailAlreadyExists.class, PasswordsDoNotMatch.class})
    public ResponseEntity<ErrorDto> handleBadRequestException(RuntimeException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.of(HttpStatus.BAD_REQUEST, ex.getMessage(), request));
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorDto> handleNotFoundException(RuntimeException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.of(HttpStatus.NOT_FOUND, ex.getMessage(), request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorDto.of(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", request));
    }
}
