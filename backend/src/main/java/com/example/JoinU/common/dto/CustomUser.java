package com.example.JoinU.common.dto;

import com.example.JoinU.doamin.user.entity.User;
import com.example.JoinU.doamin.user.entity.UserAuth;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Data
@Transactional
public class CustomUser implements UserDetails {
    private final User user;

    // 권한 getter 메소드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserAuth> authList = user.getUserAuth();

        // SimpleGrantedAuthority :: "ROLE_USER" 값 필요
        Collection<SimpleGrantedAuthority> roleList = authList.stream()
                .map((auth) -> new SimpleGrantedAuthority(auth.getAuth()))
                .toList();

        return roleList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled() != 0;
    }
}
