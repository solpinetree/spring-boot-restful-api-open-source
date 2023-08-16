package com.wantedpreonboardingbackend.fixture;

import com.wanted.sol.domain.User;

public class UserFixture {

    public static User get() {
        User result = User.of("email@email.com", "password");
        return result;
    }
}
