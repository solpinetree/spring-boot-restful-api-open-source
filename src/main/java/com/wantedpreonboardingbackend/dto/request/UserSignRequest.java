package com.wantedpreonboardingbackend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserSignRequest(

        @Email
        String email,

        @Size(min = 8)
        String password
) {
}
