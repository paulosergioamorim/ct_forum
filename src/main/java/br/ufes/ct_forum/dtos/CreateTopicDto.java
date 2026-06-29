package br.ufes.ct_forum.dtos;

public record CreateTopicDto(String title, String content, Long authorId, String[] tags) {
}
