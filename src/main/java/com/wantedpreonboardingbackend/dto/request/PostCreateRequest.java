package com.wantedpreonboardingbackend.dto.request;

import jakarta.validation.constraints.Size;

public record PostCreateRequest(

        @Size(min = 2, max = 20, message = "2글자 이상이면서 20글자 이하여야 합니다.")
        String title,

        @Size(min = 10, max = 255, message = "10글자 이상이면서 255글자 이하여야 합니다.")
        String body
) {
}
