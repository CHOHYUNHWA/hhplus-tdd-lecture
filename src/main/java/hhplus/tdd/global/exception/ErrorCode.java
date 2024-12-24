package hhplus.tdd.global.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다."),
    ALREADY_REGISTERED(HttpStatus.CONFLICT, "이미 특강을 신청하셨습니다.");

    private final HttpStatus httpStatus;
    private final String message;


}
