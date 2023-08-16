package com.wantedpreonboardingbackend.service;

import com.wanted.sol.domain.Post;
import com.wanted.sol.domain.User;
import com.wanted.sol.dto.request.PostCreateRequest;
import com.wanted.sol.exception.ErrorCode;
import com.wanted.sol.exception.GlobalApplicationException;
import com.wanted.sol.repository.PostRepository;
import com.wantedpreonboardingbackend.fixture.PostFixture;
import com.wantedpreonboardingbackend.fixture.UserFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

   @Mock
    private UserService userService;

    @Test
    void 포스트작성이_성공한경우() {
        Post fixture = PostFixture.get();

        when(userService.loadUserByEmail(any())).thenReturn(mock(User.class));
        when(postRepository.save(any())).thenReturn(mock(Post.class));

        Assertions.assertDoesNotThrow(() ->
                postService.create(
                        new PostCreateRequest(fixture.getTitle(), fixture.getBody()), fixture.getUser().getEmail()
                ));

    }

    @Test
    void 포스트작성시_요청한유저가_존재하지않는경우_에러반환() {
        Post fixture = PostFixture.get();

        when(userService.loadUserByEmail(any())).thenThrow(new GlobalApplicationException(ErrorCode.USER_NOT_FOUND, ""));

        GlobalApplicationException e = Assertions.assertThrows(GlobalApplicationException.class, () ->
                postService.create(
                        new PostCreateRequest(fixture.getTitle(), fixture.getBody()), fixture.getUser().getEmail()
                ));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트수정이_성공한경우() {
        Post fixture = PostFixture.get();

        when(userService.loadUserByEmail(any())).thenReturn(fixture.getUser());
        when(postRepository.findById(any())).thenReturn(Optional.of(fixture));
        when(postRepository.saveAndFlush(any())).thenReturn(fixture);

        Assertions.assertDoesNotThrow(() ->
                postService.modify(
                        fixture.getId(), new PostCreateRequest("newTitle", "newBodyBodyBody"), fixture.getUser().getEmail()));
    }

    @Test
    void 포스트수정시_포스트가_존재하지않는_경우_에러반환() {
        Post fixture = PostFixture.get();

        when(postRepository.findById(any())).thenReturn(Optional.empty());

        GlobalApplicationException e = Assertions.assertThrows(GlobalApplicationException.class, () ->
                postService.modify(
                        fixture.getId(), new PostCreateRequest("newTitle", "newBodyBodyBody"), fixture.getUser().getEmail()));
        Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트수정시_권한이_없는_경우_에러반환() {
        Post post = PostFixture.get();
        User user = UserFixture.get();
        ReflectionTestUtils.setField(user, "id", post.getUser().getId()+1);

        when(userService.loadUserByEmail(any())).thenReturn(user);
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        GlobalApplicationException e = Assertions.assertThrows(GlobalApplicationException.class, () ->
                postService.modify(
                        post.getId(), new PostCreateRequest("newTitle", "newBodyBodyBody"), post.getUser().getEmail()));
        Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
    }

    @Test
    void 포스트삭제가_성공한경우() {
        Post fixture = PostFixture.get();

        when(userService.loadUserByEmail(any())).thenReturn(fixture.getUser());
        when(postRepository.findById(any())).thenReturn(Optional.of(fixture));
        doNothing().when(postRepository).delete(fixture);

        Assertions.assertDoesNotThrow(() -> postService.delete(fixture.getId(), fixture.getUser().getEmail()));
    }

    @Test
    void 포스트삭제시_포스트가_존재하지않는_경우_에러반환() {
        Post fixture = PostFixture.get();

        when(postRepository.findById(any())).thenReturn(Optional.empty());

        GlobalApplicationException e = Assertions.assertThrows(GlobalApplicationException.class, () -> postService.delete(fixture.getId(), fixture.getUser().getEmail()));
        Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트삭제시_권한이_없는_경우() {
        Post post = PostFixture.get();
        User user = UserFixture.get();
        ReflectionTestUtils.setField(user, "id", post.getUser().getId()+1);

        when(userService.loadUserByEmail(any())).thenReturn(user);
        when(postRepository.findById(any())).thenReturn(Optional.of(post));

        GlobalApplicationException e = Assertions.assertThrows(GlobalApplicationException.class, () -> postService.delete(post.getId(), user.getEmail()));
        Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
    }

    @Test
    void 피드목록요청이_성공한경우() {
        Pageable pageable = mock(Pageable.class);
        when(postRepository.findAll(pageable)).thenReturn(Page.empty());
        Assertions.assertDoesNotThrow(() -> postService.list(pageable));
    }
}
