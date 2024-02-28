package com.example.JoinU.common.service;

import com.example.JoinU.common.dto.CustomUser;
import com.example.JoinU.doamin.user.entity.User;
import com.example.JoinU.doamin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    //Spring security 에서 AuthenticationManager 가 Authentication 메소드 호출 시 인증하기 위해 해당 로직 사용
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다 : " + username);
        }

        // Users (CustomDto) -> CustomUser (SecurityDto)
        return new CustomUser(user);
    }
}


