package com.wantedpreonboardingbackend.controller;

import com.wantedpreonboardingbackend.dto.Response;
import com.wantedpreonboardingbackend.dto.request.UserSignRequest;
import com.wantedpreonboardingbackend.dto.response.UserResponse;
import com.wantedpreonboardingbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/join")
    public Response<UserResponse> join(@Valid @RequestBody UserSignRequest request) {
        return Response.success(userService.join(request));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/login")
    public Response<UserResponse> login(@Valid @RequestBody UserSignRequest request) {
        return Response.success(userService.login(request));
    }
}
