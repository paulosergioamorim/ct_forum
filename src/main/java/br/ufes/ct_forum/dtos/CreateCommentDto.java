package br.ufes.ct_forum.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO responsável por encapsular o payload enviado pelo cliente HTTP para a
 * criação de um novo comentário ou resposta.
 *
 * @param content  O conteúdo em texto do comentário.
 * @param authorId O identificador único do usuário que está submetendo o comentário.
 * @param parentId O identificador do comentário original que está sendo respondido (reply). 
 * É opcional; se for nulo, indica que é um comentário raiz (top-level).
 */
@Schema(description = "Dto para criação de comentário")
public record CreateCommentDto(
        @Schema(description = "Conteúdo do comentário", example = "Concordo com o que você disse!") String content,
        @Schema(description = "ID do autor do comentário", example = "1") Long authorId,
        @Schema(description = "ID do post comentário (opcional)", example = "2") Long parentId) {
}
