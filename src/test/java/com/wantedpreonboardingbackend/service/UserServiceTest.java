package com.wantedpreonboardingbackend.service;

import com.wanted.sol.domain.User;
import com.wanted.sol.dto.request.UserSignRequest;
import com.wanted.sol.exception.ErrorCode;
import com.wanted.sol.exception.GlobalApplicationException;
import com.wanted.sol.repository.UserRepository;
import com.wantedpreonboardingbackend.fixture.UserFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "secretKey", "secret_keysecret_keysecret_keysecret_keysecret_keysecret_keysecret_key");
        ReflectionTestUtils.setField(userService, "expiredTimeMs", 2592000000L);
    }

    @Test
    void 회원가입이_정상적으로_동작하는_경우() {
        User user = UserFixture.get();

        when(userRepository.save(any())).thenReturn(user);

        Assertions.assertDoesNotThrow(() -> userService.join(new UserSignRequest(user.getEmail(), user.getPassword())));
    }

    @Test
    void 회원가입시_email으로_회원가입한_유저가_이미_있는경우_에러반환() {
        doThrow(new DataIntegrityViolationException("")).when(userRepository).save(any());

        GlobalApplicationException e = Assertions.assertThrows(GlobalApplicationException.class, () -> userService.join(new UserSignRequest("email@email.com","password")));
        Assertions.assertEquals(ErrorCode.DUPLICATED_EMAIL, e.getErrorCode());
    }

    @Test
    void 로그인이_정상적으로_동작하는_경우() {
        User fixture = UserFixture.get();

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(fixture));
        when(encoder.matches(any(), any())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> userService.login(new UserSignRequest(fixture.getEmail(), fixture.getPassword())));
    }

    @Test
    void 로그인시_email으로_회원가입한_유저가_없는_경우_에러반환() {
        User fixture = UserFixture.get();

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        GlobalApplicationException e = Assertions.assertThrows(GlobalApplicationException.class, () -> userService.login(new UserSignRequest(fixture.getEmail(), fixture.getPassword())));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 로그인시_패스워드가_틀린_경우_에러반환() {
        User fixture = UserFixture.get();
        String wrongPassword = fixture.getPassword() + "wrongPassword";

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(fixture));

        GlobalApplicationException e = Assertions.assertThrows(GlobalApplicationException.class, () -> userService.login(new UserSignRequest(fixture.getEmail(), wrongPassword)));
        Assertions.assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());
    }
}