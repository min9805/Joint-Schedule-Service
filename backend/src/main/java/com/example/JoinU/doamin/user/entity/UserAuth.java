package com.example.JoinU.doamin.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String auth;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Builder
    public UserAuth(String auth, User user) {
        this.auth = auth;
        this.user = user;
    }

    public static UserAuth createUserAuth(User user) {
        return UserAuth.builder()
                .auth("ROLE_USER")
                .user(user)
                .build();
    }
}
