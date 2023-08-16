package com.wantedpreonboardingbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GlobalApplicationException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;
}
