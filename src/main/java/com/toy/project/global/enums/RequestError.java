package com.toy.project.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RequestError {
    INVALID_AUTHORIZE(HttpStatus.UNAUTHORIZED, "CLIENT_001","요청자의 인증정보가 유효하지 않습니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "CLIENT_002", "요소를 찾을 수 없습니다."),
    DUPLICATE_DATA(HttpStatus.BAD_REQUEST, "CLIENT_003","중복되는 데이터를 입력받았습니다."),
    ACCESSTOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "CLIENT_004", "Access Token이 만료되었습니다."),
    REFRESHTOKEN_EXPIRED(HttpStatus.FORBIDDEN, "CLIENT_005", "Refresh Token이 만료되었습니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "CLIENT_006", "로그인에 실패하였습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_001", "서버에서 예외처리되지않은 오류가 발생하였습니다."),
    DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_002","데이터 삭제에 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}