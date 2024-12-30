package hhplus.tdd.application.dto;

import java.time.LocalDateTime;

public record LectureRegisterResponse(
        long registrationId,
        LocalDateTime registrationDt
) {
}
