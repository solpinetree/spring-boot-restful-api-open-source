package com.wantedpreonboardingbackend.fixture;


import com.wanted.sol.domain.Post;
import com.wanted.sol.domain.User;
import org.springframework.test.util.ReflectionTestUtils;

public class PostFixture {

    public static Post get() {
        User user = User.of("email@email.com", "password");
        ReflectionTestUtils.setField(user, "id", 1L);

        Post result = Post.of("title", "bodybodybody", user);
        ReflectionTestUtils.setField(result, "id", 1L);
        return result;
    }
}
