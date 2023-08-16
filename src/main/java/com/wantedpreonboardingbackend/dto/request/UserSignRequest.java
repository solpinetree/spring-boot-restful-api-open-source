package com.wantedpreonboardingbackend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserSignRequest(

        @Email(message = "잘못된 이메일 형식 입니다.")
        String email,

        @Size(min = 8, max = 255, message = "8글자 이상이면서 255글자 이하여야 합니다.")
        String password
) {
}
