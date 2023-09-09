package com.socialnetwork.sns.controller.response;

import com.socialnetwork.sns.model.Post;
import com.socialnetwork.sns.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponse {
    private Long id;

    private String title;

    private String body;

    private UserResponse user;

    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;

    private LocalDateTime removedAt;

    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                UserResponse.fromUser(post.getUser()),
                post.getRegisteredAt(),
                post.getUpdatedAt(),
                post.getRemovedAt()
        );
    }
}
