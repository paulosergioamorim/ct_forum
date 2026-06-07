package br.ufes.ct_forum.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dto para criação de tópico")
public record CreateTopicDto(@Schema(description = "Título do tópico", example = "### Olá mundo!") String title,
                             @Schema(description = "Conteúdo do tópico", example = "Adeus!") String content,
                             @Schema(description = "ID do autor do tópico", example = "1") long authorId,
                             @Schema(description = "Tags do tópico", example = "[\"java\", \"spring-boot\"]") String[] tags) {
}
