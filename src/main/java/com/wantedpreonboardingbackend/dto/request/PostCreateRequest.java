package com.wantedpreonboardingbackend.dto.request;

import jakarta.validation.constraints.Size;

public record PostCreateRequest(

        @Size(min = 2, max = 20)
        String title,

        @Size(min = 10, max = 255)
        String body
) {
}
