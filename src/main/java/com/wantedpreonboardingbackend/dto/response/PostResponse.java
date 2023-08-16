package com.wantedpreonboardingbackend.dto.response;

import com.wantedpreonboardingbackend.domain.Post;
import com.wantedpreonboardingbackend.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record PostResponse(

        @NotNull
        Long id,

        @NotBlank
        String title,

        @NotBlank
        String Body,

        @NotNull
        UserResponse user,

        @NotNull
        Timestamp registeredAt,

        @NotNull
        Timestamp updatedAt
) {

        public static PostResponse from(Post post) {
                return from(post, post.getUser());
        }

        public static PostResponse from(Post post, User user) {
                return new PostResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getBody(),
                        UserResponse.from(user),
                        post.getRegisteredAt(),
                        post.getUpdatedAt()
                );
        }
}
