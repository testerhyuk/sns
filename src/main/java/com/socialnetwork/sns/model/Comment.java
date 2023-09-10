package com.socialnetwork.sns.model;

import com.socialnetwork.sns.model.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Comment {
    private Long id;

    private String comment;

    private String userName;

    private Long postId;

    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;

    private LocalDateTime removedAt;

    public static Comment fromEntity(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getComment(),
                entity.getUser().getUserName(),
                entity.getPost().getId(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getRemovedAt()
        );
    }
}
