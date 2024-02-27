package com.example.JoinU.controller;



import com.example.JoinU.constants.SecurityConstants;
import com.example.JoinU.doamin.AuthenticationRequest;
import com.example.JoinU.prop.JwtProp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private JwtProp jwtProp;

    /**
     * /login
     * -username
     * -password
     */
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        log.info("username : " + username);

        //사용자 권한
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");

        byte[] signingKey = jwtProp.getSecretKey().getBytes();

        String jwt = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), Jwts.SIG.HS256)
                .header()
                .add("typ", SecurityConstants.TOKEN_TYPE)
                .and()
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 5))
                .claim("uid", username)
                .claim("rol", roles)
                .compact();

        return new ResponseEntity<String>(jwt, HttpStatus.OK);
    }

    @GetMapping("/user/info")
    public ResponseEntity<?> userInfo(@RequestHeader(name = "Authorization") String header) {
        log.info("=====header=====");
        log.info("Authorization : " + header);

        // Authorization : Bearer ${jwt}
        String jwt = header.replace(SecurityConstants.TOKEN_PREFIX, "").trim();
        byte[] signingKey = jwtProp.getSecretKey().getBytes();

        Jws<Claims> parsedToken = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(signingKey))
                .build()
                .parseSignedClaims(jwt);

        String username = parsedToken.getPayload().get("uid").toString();
        log.info("username :" + username);

        Claims claims = parsedToken.getPayload();
        Object roles = claims.get("rol");
        log.info("roles :" + roles);

        return new ResponseEntity<String>(parsedToken.toString(), HttpStatus.OK);
    }
}
