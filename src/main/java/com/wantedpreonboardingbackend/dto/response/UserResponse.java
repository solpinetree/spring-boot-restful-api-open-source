package com.wantedpreonboardingbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wantedpreonboardingbackend.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        @NotNull
        Long userId,

        @Email
        String email,

        String token
) {

    public static UserResponse from(User user) {
        return from(user, null);
    }

    public static UserResponse from(User user, String token) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                token
        );
    }
}
