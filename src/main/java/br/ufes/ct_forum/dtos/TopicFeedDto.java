package br.ufes.ct_forum.dtos;

import java.time.LocalDateTime;

/**
 * DTO de leitura focado na exibição dos cartões de tópico no Feed.
 */
public record TopicFeedDto(
        long id,
        String title,
        String authorName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean isEdited,
        long commentCount
) {
}
