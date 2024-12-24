package hhplus.tdd.global.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다."),
    ALREADY_REGISTERED(HttpStatus.CONFLICT, "이미 특강을 신청하셨습니다."),
    LECTURE_ALREADY_ENDED(HttpStatus.BAD_REQUEST, "해당 강의는 이미 종료되었습니다."),
    EXCEEDED_REGISTRATION_LIMIT(HttpStatus.BAD_REQUEST, "잔여 신청 인원 수가 부족합니다."),
    NOT_EXIST_LECTURE(HttpStatus.NOT_FOUND, "존재하지 않는 강의 입니다.");

    private final HttpStatus httpStatus;
    private final String message;


}
