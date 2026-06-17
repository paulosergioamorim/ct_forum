package br.ufes.ct_forum.dtos;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * DTO utilizado para encapsular os dados enviados pelo cliente 
 * na requisição de criação de um novo Tópico.
 *
 * @param title    O título principal da discussão.
 * @param content  O corpo de texto contendo a pergunta ou discussão detalhada.
 * @param authorId O identificador do usuário que está criando o tópico.
 * @param tags     Vetor de categorias (ex: "java", "spring") associadas ao tópico.
 */
@Schema(description = "Dto para criação de tópico")
public record CreateTopicDto(@Schema(description = "Título do tópico", example = "### Olá mundo!") String title,
                             @Schema(description = "Conteúdo do tópico", example = "Adeus!") String content,
                             @Schema(description = "ID do autor do tópico", example = "1") long authorId,
                             @Schema(description = "Tags do tópico", example = "[\"java\", \"spring-boot\"]") String[] tags) {
}
