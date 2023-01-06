package com.numble.carot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ILLEGAL_ARGUMENT_KEY(HttpStatus.BAD_REQUEST, "유효하지 않은 Key 입니다."),
    NOT_FOUND_RESOURCE(HttpStatus.NOT_FOUND, "해당 파일을 찾을 수 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "유효하지 않은 메일 형식입니다."),
    ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다."),
    NO_MATCH_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    NOT_FOUND_ITEM(HttpStatus.BAD_REQUEST, "삭제된 게시물 입니다.");


    private final HttpStatus status;
    private final String message;

}
