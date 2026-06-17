package br.ufes.ct_forum.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO responsável por receber os dados da requisição para registrar ou 
 * atualizar uma avaliação em uma publicação.
 *
 * @param postId     O identificador da publicação (Tópico ou Comentário) sendo avaliada.
 * @param userId     O identificador do usuário que está emitindo a avaliação.
 * @param isPositive Indica se a avaliação é positiva (true) ou negativa (false).
 */
@Schema(description = "Dto para criação de avaliação de post")
public record CreateRatingDto(@Schema(description = "ID do post a se avaliar", example = "5") long postId,
                              @Schema(description = "ID do usuário a avaliar o post", example = "10") long userId,
                              @Schema(description = "Se a avaliação é positiva (like)", example = "false") boolean isPositive) {
}
