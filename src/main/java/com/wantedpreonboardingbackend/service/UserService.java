package com.wantedpreonboardingbackend.service;

import com.wantedpreonboardingbackend.domain.User;
import com.wantedpreonboardingbackend.dto.request.UserSignRequest;
import com.wantedpreonboardingbackend.dto.response.UserResponse;
import com.wantedpreonboardingbackend.exception.ErrorCode;
import com.wantedpreonboardingbackend.exception.GlobalApplicationException;
import com.wantedpreonboardingbackend.repository.UserRepository;
import com.wantedpreonboardingbackend.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserResponse join(UserSignRequest request) {
        User user = null;

        try {
            user = userRepository.save(User.of(request.email(), encoder.encode(request.password())));
        } catch (DataIntegrityViolationException e) {
            throw new GlobalApplicationException(ErrorCode.DUPLICATED_EMAIL, "사용 중인 이메일 입니다.");
        }

        return UserResponse.from(user);
    }

    public UserResponse login(UserSignRequest request) {
        User user = loadUserByEmail(request.email());

        // 비밀번호 체크
        if(!encoder.matches(request.password(), user.getPassword())){
            throw new GlobalApplicationException(ErrorCode.INVALID_PASSWORD, "비밀번호가 일치하지 않습니다.");
        }

        return UserResponse.from(user, JwtTokenUtils.generateToken(user.getEmail(), secretKey, expiredTimeMs));
    }

    public User loadUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->
                new GlobalApplicationException(ErrorCode.USER_NOT_FOUND, String.format("존재하지 않는 이메일 입니다.", email)));

        return user;
    }
}
