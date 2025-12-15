package com.mysite.backend.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        //에러 생성시 에러 메세지를 만듬
        super("id에 맞는 유저가 없습니다." + id);
    }
}
