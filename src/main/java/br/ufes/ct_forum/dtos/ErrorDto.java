package br.ufes.ct_forum.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Schema(description = "Erro do sistema")
public record ErrorDto(
        @Schema(description = "Timestamp atual", example = "2026-06-07T13:46:13.162686926Z") Instant timestamp,
        @Schema(description = "Status code do erro", example = "404") int status,
        @Schema(description = "Descrição do erro", example = "Usuário com id 1 não encontrado") String error,
        @Schema(description = "Caminho para o endpoint que gerou o erro", example = "/users/1") String path) {
    public static ErrorDto of(HttpStatus status, String message, HttpServletRequest request) {
        return new ErrorDto(Instant.now(), status.value(), message, request.getRequestURI());
    }
}
