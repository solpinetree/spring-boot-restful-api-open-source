package com.wantedpreonboardingbackend.dto;

import com.wantedpreonboardingbackend.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record UserDto(
        Long id,
        String email
) implements UserDetails {

    @Override public String getUsername() { return email; }
    @Override public String getPassword() { return null; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail()
        );
    }
}