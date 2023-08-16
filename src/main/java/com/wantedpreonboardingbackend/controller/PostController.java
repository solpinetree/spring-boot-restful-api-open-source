package com.wantedpreonboardingbackend.controller;

import com.wantedpreonboardingbackend.dto.Response;
import com.wantedpreonboardingbackend.dto.request.PostCreateRequest;
import com.wantedpreonboardingbackend.dto.response.PostResponse;
import com.wantedpreonboardingbackend.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Response<PostResponse> create(@Valid @RequestBody PostCreateRequest request, Authentication authentication) {
        return Response.success(postService.create(request, authentication.getName()));
    }

    @GetMapping
    public Response<Page<PostResponse>> list(
            @ParameterObject @PageableDefault(sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        return Response.success(postService.list(pageable));
    }

    @GetMapping("/{postId}")
    public Response<PostResponse> detail(
            @PathVariable Long postId
    ){
        return Response.success(postService.detail(postId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{postId}")
    public Response<PostResponse> modify(
            @PathVariable Long postId,
            @Valid @RequestBody PostCreateRequest request,
            Authentication authentication
    ){
        return Response.success(postService.modify(postId, request, authentication.getName()));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{postId}")
    public Response<Void> delete(
            @PathVariable Long postId,
            Authentication authentication
    ){
        postService.delete(postId, authentication.getName());
        return Response.success();
    }
}
