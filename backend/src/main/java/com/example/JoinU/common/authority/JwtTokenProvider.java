package com.example.JoinU.common.authority;

import com.example.JoinU.common.constants.JwtConstants;
import com.example.JoinU.common.constants.SecurityConstants;
import com.example.JoinU.common.dto.CustomUser;
import com.example.JoinU.common.prop.JwtProp;
import com.example.JoinU.doamin.AuthenticationRequest;
import com.example.JoinU.doamin.user.entity.User;
import com.example.JoinU.doamin.user.entity.UserAuth;
import com.example.JoinU.doamin.user.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 토큰 관련 기능
 * <p>
 * - 토큰 생성
 * - 토큰 해석
 * - 토큰 유효성 검사
 */
@Slf4j
@Component
public class JwtTokenProvider {

    @Autowired
    private JwtProp jwtProp;
    @Autowired
    private UserRepository userRepository;

    public String createToken(Long uid, String username, List<String> roles) {
        byte[] signingKey = getSigningKey();

        return Jwts.builder()
                .signWith(getShaKey(signingKey), Jwts.SIG.HS256)
                .header().add("typ", JwtConstants.TOKEN_TYPE)
                .and()
                .expiration(new Date(System.currentTimeMillis() + JwtConstants.TOKEN_EXPIRATION))
                .claim("uid", uid)
                .claim("rol", roles)
                .claim("username", username)
                .compact();
    }


    @Transactional
    public UsernamePasswordAuthenticationToken getAuthentication(String header) {
        if (header == null || header.isEmpty()) {
            return null;
        }

        try {

            String jwt = header.replace(JwtConstants.TOKEN_PREFIX, "").trim();

            Jws<Claims> parsedToken = Jwts.parser()
                    .verifyWith(getShaKey(getSigningKey()))
                    .build()
                    .parseSignedClaims(jwt);

            Claims claims = parsedToken.getPayload();

            Object roles = claims.get("rol");
            log.info("roles :" + roles);

            Long uid = (Long) claims.get("uid");
            log.info("uid : " + uid);

            String username = claims.get("username").toString();
            log.info("username : " + username);

            if (username == null || username.isEmpty()) {
                return null;
            }

            User user = User.builder()
                    .id(uid)
                    .name(username)
                    .build();

            List<UserAuth> authList = ((List<UserAuth>) roles).stream()
                    .map(auth -> new UserAuth(auth.toString(), user))
                    .toList();

            //CustomUser 에 권한 담기
            List<SimpleGrantedAuthority> authorities = ((List<UserAuth>) roles).stream()
                    .map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
                    .toList();

            try {
                User userInfo = userRepository.findById(uid).orElse(null);
                if (userInfo != null) {
                    user.toBuilder()
                            .email(userInfo.getEmail())
                            .build();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("토큰 유효 -> DB 추가 정보 조회 시 에러 발생");
            }

            UserDetails userDetails = new CustomUser(user);

            return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        } catch (ExpiredJwtException exception) {
            log.warn("Request to parse expired JWT : {} failed : {}", header, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", header, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.warn("Request to parse invalid JWT : {} failed : {}", header, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.warn("Request to parse empty or null JWT : {} failed : {}", header, exception.getMessage());
        }
        return null;
    }

    /**
     * 토큰 유효성 검사 (만료기간 검사)
     */
    public boolean validateToken(String jwt) {
        try {


            Jws<Claims> parsedToken = Jwts.parser()
                    .verifyWith(getShaKey(getSigningKey()))
                    .build()
                    .parseSignedClaims(jwt);

            log.info("#### 토큰 만료 기간 ####");
            Date expiration = parsedToken.getPayload().getExpiration();
            log.info("-> " + expiration);

            return expiration.after(new Date());
        } catch (ExpiredJwtException exception) {
            log.warn("Request to parse expired JWT failed : {}", exception.getMessage());
            return false;
        } catch (JwtException exception) {
            log.warn("Request to parse tampered JWT failed : {}", exception.getMessage());
            return false;
        } catch (NullPointerException exception) {
            log.warn("Request to parse null JWT failed : {}", exception.getMessage());
            return false;
        }
    }

    private static SecretKey getShaKey(byte[] signingKey) {
        return Keys.hmacShaKeyFor(signingKey);
    }

    private byte[] getSigningKey() {
        return jwtProp.getSecretKey().getBytes();
    }
}
