package com.wantedpreonboardingbackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Table(indexes = {
        @Index(columnList = "email", unique = true)
})
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Email(message = "INVALID_EMAIL")
    @Column(nullable = false)
    private String email;

    @NotNull
    @Size(min = 8, message = "INVALID_PASSWORD")
    @Column(nullable = false)
    private String password;

    protected User() {}

    private User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static User of(String email, String password) {
        return new User(
                email,
                password
        );
    }
}
