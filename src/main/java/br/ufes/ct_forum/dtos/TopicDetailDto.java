package br.ufes.ct_forum.dtos;

import java.time.LocalDateTime;
import java.util.List;


public record TopicDetailDto(
        long id,
        String title,
        String content,
        String authorName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean isEdited,
        String[] tags,
        List<CommentDto> comments
) {
}
