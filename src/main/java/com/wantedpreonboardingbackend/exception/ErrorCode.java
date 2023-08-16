package com.wantedpreonboardingbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED),
    PUSH_ALARM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private HttpStatus status;
}
