package com.wantedpreonboardingbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.sol.dto.request.UserSignRequest;
import com.wanted.sol.dto.response.UserResponse;
import com.wanted.sol.exception.ErrorCode;
import com.wanted.sol.exception.GlobalApplicationException;
import com.wanted.sol.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void 회원가입() throws Exception {
        String email = "eamil@email.com";
        String password = "password";
        UserSignRequest request = new UserSignRequest(email, password);

        when(userService.join(request)).thenReturn(mock(UserResponse.class));

        mockMvc.perform(post("/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void 회원가입시_이메일_잘못된_형식_입력_하는경우_에러반환() throws Exception {
        String email = "eamil";
        String password = "password";
        UserSignRequest request = new UserSignRequest(email, password);

        mockMvc.perform(post("/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 회원가입시_짧은_비밀번호_입력_하는경우_에러반환() throws Exception {
        String email = "eamil@email.com";
        String password = "passwod";
        UserSignRequest request = new UserSignRequest(email, password);

        mockMvc.perform(post("/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 회원가입시_이미_회원가입된_email로_회원가입을_하는경우_에러반환() throws Exception {
        String email = "eamil@email.com";
        String password = "password";
        UserSignRequest request = new UserSignRequest(email, password);

        when(userService.join(request)).thenThrow(new GlobalApplicationException(ErrorCode.DUPLICATED_EMAIL, "사용 중인 이메일 입니다."));

        mockMvc.perform(post("/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void 로그인() throws Exception{
        String email = "eamil@email.com";
        String password = "password";
        UserSignRequest request = new UserSignRequest(email, password);
        UserResponse response = new UserResponse(1L, email, "test_token");

        when(userService.login(request)).thenReturn(response);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void 로그인시_회원가입이_안된_email을_입력할경우_에러반환() throws Exception {
        String email = "eamil@email.com";
        String password = "password";
        UserSignRequest request = new UserSignRequest(email, password);

        when(userService.login(request)).thenThrow(new GlobalApplicationException(ErrorCode.USER_NOT_FOUND, "존재하지 않는 이메일 입니다."));

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void 로그인시_틀린_password를_입력할경우_에러반환() throws Exception {
        String email = "eamil@email.com";
        String password = "password";
        UserSignRequest request = new UserSignRequest(email, password);

        when(userService.login(request)).thenThrow(new GlobalApplicationException(ErrorCode.INVALID_PASSWORD, "비밀번호가 일치하지 않습니다."));

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}