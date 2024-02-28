package com.example.JoinU.doamin.user.repository;

import com.example.JoinU.doamin.user.entity.User;
import com.example.JoinU.doamin.user.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
}
