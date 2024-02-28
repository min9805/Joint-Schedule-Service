package com.example.JoinU.doamin.user.controller;

import com.example.JoinU.common.dto.CustomUser;
import com.example.JoinU.doamin.user.dto.UserJoinDto;
import com.example.JoinU.doamin.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/info")
    public ResponseEntity<?> userInfo(@AuthenticationPrincipal CustomUser customUserm) {


        return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserJoinDto user) throws Exception {
        userService.insert(user);

        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }
}
