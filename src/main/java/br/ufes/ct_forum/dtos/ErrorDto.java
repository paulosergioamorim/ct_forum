package br.ufes.ct_forum.dtos;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ErrorDto(
        Instant timestamp,
        int status,
        String error,
        String path
) {
    public static ErrorDto of(HttpStatus status, String message, HttpServletRequest request) {
        return new ErrorDto(
                Instant.now(),
                status.value(),
                message,
                request.getRequestURI()
        );
    }
}
