package com.wantedpreonboardingbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.sol.dto.request.PostCreateRequest;
import com.wanted.sol.dto.response.PostResponse;
import com.wanted.sol.exception.ErrorCode;
import com.wanted.sol.exception.GlobalApplicationException;
import com.wanted.sol.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @Test
    @WithMockUser
    void 포스트작성() throws Exception {
        String title = "title";
        String body = "bodybodybody";
        PostCreateRequest request = new PostCreateRequest(title, body);

        when(postService.create(request, "user@gmail.com")).thenReturn(mock(PostResponse.class));

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void 포스트작성시_짧은_제목_입력_하는경우_에러반환() throws Exception {
        String title = "t";
        String body = "bodybodybody";
        PostCreateRequest request = new PostCreateRequest(title, body);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void 포스트작성시_짧은_내용_입력_하는경우_에러반환() throws Exception {
        String title = "title";
        String body = "bodybo";
        PostCreateRequest request = new PostCreateRequest(title, body);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithAnonymousUser
    void 포스트작성시_로그인하지않은경우() throws Exception {
        String title = "title";
        String body = "bodybodybody";
        PostCreateRequest request = new PostCreateRequest(title, body);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void 포스트수정() throws Exception {
        String title = "title";
        String body = "bodybodybody";
        PostCreateRequest request = new PostCreateRequest(title, body);

        when(postService.modify(any(), eq(request), any())).thenReturn(mock(PostResponse.class));

        mockMvc.perform(put("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithAnonymousUser
    void 포스트수정시_로그인하지않은경우_에러반환() throws Exception {
        String title = "title";
        String body = "bodybodybody";
        PostCreateRequest request = new PostCreateRequest(title, body);

        mockMvc.perform(post("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void 포스트수정시_본인이_작성한_글이_아니라면_에러반환() throws Exception {
        String title = "title";
        String body = "bodybodybody";
        PostCreateRequest request = new PostCreateRequest(title, body);

        doThrow(new GlobalApplicationException(ErrorCode.INVALID_PERMISSION, "수정할 권한이 없습니다.")).when(postService).modify(any(), eq(request), any());

        mockMvc.perform(put("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void 포스트수정시_수정하려는_글이_없는경우_에러반환() throws Exception {
        String title = "title";
        String body = "bodybodybody";
        PostCreateRequest request = new PostCreateRequest(title, body);

        doThrow(new GlobalApplicationException(ErrorCode.POST_NOT_FOUND, "존재하지 않는 게시글 입니다.")).when(postService).modify(any(), eq(request), any());

        mockMvc.perform(put("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void 포스트삭제() throws Exception {
        mockMvc.perform(delete("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithAnonymousUser
    void 포스트삭제시_로그인하지_않은경우_에러반환() throws Exception {
        mockMvc.perform(delete("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void 포스트삭제시_작성자와_삭제요청자가_다를경우_에러반환() throws Exception {
        doThrow(new GlobalApplicationException(ErrorCode.INVALID_PERMISSION, "삭제할 권한이 없습니다.")).when(postService).delete(any(), any());

        mockMvc.perform(delete("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void 포스트삭제시_삭제하려는_포스트가_존재하지_않을_경우_에러반환() throws Exception {
        doThrow(new GlobalApplicationException(ErrorCode.POST_NOT_FOUND, "존재하지 않는 게시글 입니다.")).when(postService).delete(any(), any());

        mockMvc.perform(delete("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser
    void 피드목록() throws Exception {
        when(postService.list(any())).thenReturn(Page.empty());

        mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void 피드목록요청시_로그인하지_않은경우_에러반환() throws Exception {
        mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}