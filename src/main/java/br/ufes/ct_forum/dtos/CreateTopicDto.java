package br.ufes.ct_forum.dtos;

public record CreateTopicDto(String title, String content, long authorId, String[] tags) {
}
