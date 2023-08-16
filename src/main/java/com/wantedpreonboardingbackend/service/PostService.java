package com.wantedpreonboardingbackend.service;

import com.wantedpreonboardingbackend.domain.Post;
import com.wantedpreonboardingbackend.domain.User;
import com.wantedpreonboardingbackend.dto.request.PostCreateRequest;
import com.wantedpreonboardingbackend.dto.response.PostResponse;
import com.wantedpreonboardingbackend.exception.ErrorCode;
import com.wantedpreonboardingbackend.exception.GlobalApplicationException;
import com.wantedpreonboardingbackend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;

    public PostResponse create(PostCreateRequest request, String email) {
        User user = userService.loadUserByEmail(email);

        return PostResponse.from(postRepository.save(Post.of(request.title(), request.body(), user)), user);
    }

    public PostResponse modify(Long postId, PostCreateRequest request, String email) {
        User user = userService.loadUserByEmail(email);
        Post post = loadPostById(postId);

        if (!post.getUser().equals(user)) {
            throw new GlobalApplicationException(ErrorCode.INVALID_PERMISSION, "수정할 권한이 없습니다.");
        }

        post.setTitle(request.title());
        post.setBody(request.body());

        return PostResponse.from(postRepository.saveAndFlush(post));
    }

    public void delete(Long postId, String email) {
        User user = userService.loadUserByEmail(email);
        Post post = loadPostById(postId);

        if (!post.getUser().equals(user)) {
            throw new GlobalApplicationException(ErrorCode.INVALID_PERMISSION, "삭제할 권한이 없습니다.");
        }

        postRepository.delete(post);
    }

    public Page<PostResponse> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponse::from);
    }

    public PostResponse detail(Long postId) {
        Post post = loadPostById(postId);

        return PostResponse.from(post);
    }

    public Post loadPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new GlobalApplicationException(ErrorCode.POST_NOT_FOUND, "존재하지 않는 게시글 입니다."));
    }
}
