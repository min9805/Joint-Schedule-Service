package com.example.JoinU.doamin.user.service;

import com.example.JoinU.doamin.user.dto.UserJoinDto;
import com.example.JoinU.doamin.user.dto.UserLoginDto;
import com.example.JoinU.doamin.user.entity.User;
import com.example.JoinU.doamin.user.entity.UserAuth;
import com.example.JoinU.doamin.user.repository.UserAuthRepository;
import com.example.JoinU.doamin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long insert(UserJoinDto user) throws Exception {

        User newUser = userRepository.save(user.toEntity()
                .toBuilder()
                .password(passwordEncoder.encode(user.getPassword()))
                .build()
        );

        if (newUser.getId() > 0) {
            userAuthRepository.save(UserAuth.createUserAuth(newUser));
        }

        return newUser.getId();
    }

    /**
     * TO-DO
     * orElse 처리
     *
     * @throws Exception
     */
    @Override
    public User select(Long userId) throws Exception {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void login(UserLoginDto user) throws Exception {

    }

    @Override
    public Long update(User user) throws Exception {
        return null;
    }

    @Override
    public Long delete(Long userId) throws Exception {
        return null;
    }
}
