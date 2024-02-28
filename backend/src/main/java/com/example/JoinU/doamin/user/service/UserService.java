package com.example.JoinU.doamin.user.service;

import com.example.JoinU.doamin.user.dto.UserJoinDto;
import com.example.JoinU.doamin.user.dto.UserLoginDto;
import com.example.JoinU.doamin.user.entity.User;

public interface UserService {
    //회원 등록
    public Long insert(UserJoinDto user) throws Exception;

    //회원 조회
    public User select(Long userId) throws Exception;

    //로그인
    public void login(UserLoginDto user) throws Exception;

    //회원 수정
    public Long update(User user) throws Exception;

    //회원 삭제
    public Long delete(Long userId) throws Exception;
}
