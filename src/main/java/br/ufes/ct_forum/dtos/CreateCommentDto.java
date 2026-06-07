package br.ufes.ct_forum.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dto para criação de comentário")
public record CreateCommentDto(
        @Schema(description = "Conteúdo do comentário", example = "Concordo com o que você disse!") String content,
        @Schema(description = "ID do autor do comentário", example = "1") long authorId,
        @Schema(description = "ID do post comentário (opcional)", example = "2") Long parentId) {
}
