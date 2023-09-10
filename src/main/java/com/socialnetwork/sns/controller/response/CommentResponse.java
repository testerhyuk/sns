package com.socialnetwork.sns.controller.response;

import com.socialnetwork.sns.model.Comment;
import com.socialnetwork.sns.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long id;

    private String comment;

    private String userName;

    private Long postId;

    private LocalDateTime registeredAt;

    private LocalDateTime updatedAt;

    private LocalDateTime removedAt;

    public static CommentResponse fromComment(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUserName(),
                comment.getPostId(),
                comment.getRegisteredAt(),
                comment.getUpdatedAt(),
                comment.getRemovedAt()
        );
    }
}