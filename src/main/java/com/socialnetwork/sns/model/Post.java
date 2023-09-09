package com.socialnetwork.sns.model;

import com.socialnetwork.sns.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Data
public class Post {
    private Long id;

    private String title;

    private String body;

    private User user;

    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;

    private LocalDateTime removedAt;

    public static Post fromEntity(PostEntity entity) {
        return new Post(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                User.fromEntity(entity.getUser()),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getRemovedAt()
        );
    }
}
