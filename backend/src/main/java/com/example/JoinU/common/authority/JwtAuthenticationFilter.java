package com.example.JoinU.common.authority;

import com.example.JoinU.common.constants.JwtConstants;
import com.example.JoinU.common.dto.CustomUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Slf4j
@Transactional
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        setFilterProcessesUrl(JwtConstants.AUTH_LOGIN_URL);
    }

    /**
     * 인증 시도 메서드
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 사용자 인증정보 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        log.info("인증 정보 객체 : " + authentication);

        // 사용자 인증 (로그인)
        // UserDetailsService 의 Custom 로직 실행
        // PasswordEncoder 에 따라 암호화
        Authentication authenticate = authenticationManager.authenticate(authentication);

        log.info("인증 여부 : " + authenticate.isAuthenticated());

        // 인증 실패 (username, password 불일치)
        if (!authenticate.isAuthenticated()) {
            log.info("인증 실패 : 아이디 또는 비밀번호가 일치하지 않습니다.");
            response.setStatus(401);
        }

        //인증 성공 시 successfulAuthentication 호출
        return authenticate;
    }

    /***
     *  인증 성공 메서드
     *
     *  - JWT 생성
     *  - JWT 를 응답 헤더에 설정
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        log.info("successfulAuthentication : " + request);

        CustomUser user = (CustomUser) authentication.getPrincipal();
        Long userId = user.getUser().getId();
        String username = user.getUsername();

        List<String> roles = user.getAuthorities()
                .stream()
                .map(auth -> auth.toString())
                .toList();

        String jwt = jwtTokenProvider.createToken(userId, username, roles);

        response.addHeader(JwtConstants.TOKEN_HEADER, JwtConstants.TOKEN_PREFIX + jwt);
        response.setStatus(200);

    }
}
