package com.example.JoinU.common.authority;

import com.example.JoinU.common.constants.JwtConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 매 요청마다 해당 필터를 사용한다.
 * 항상 JWT 를 검증한다는 뜻
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * JWT 요청 필터
     * - request > header > Authrization
     * - JWT 토큰 유효성 검사
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(JwtConstants.TOKEN_HEADER);

        //JWT 토큰이 없으면 다음 필터로 이동 ( 로그인 과정 )
        if (header == null || header.isEmpty() || !header.startsWith(JwtConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = header.replace(JwtConstants.TOKEN_PREFIX, "");

        Authentication authentication = jwtTokenProvider.getAuthentication(jwt);

        if (jwtTokenProvider.validateToken(jwt)) {
            log.info("유효한 JWT 필터입니다.");

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }

}
