package br.ufes.ct_forum.dtos;

import java.time.LocalDateTime;


public record CommentDto(
        long id,
        String authorName,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean isEdited
) {
}
