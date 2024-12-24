package hhplus.tdd.domain.model;

import hhplus.tdd.global.exception.CustomException;
import hhplus.tdd.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class LectureTest {

    private static final Long LECTURE_ID = 1L;
    private static final String LECTURE_TITLE = "Good Lecture";
    private static final String LECTURER = "Mr.James";
    private static final LocalDateTime LECTURE_DATE = LocalDateTime.now().plusDays(1);
    private static final Integer LECTURE_CAPACITY = 30;


    @Test
    @DisplayName("이미 지난 강의 신청 시 검증 실패")
    void shouldFailValidationWhenRegisteringForPastLecture() {
        // given
        Lecture lecture = Lecture.builder()
                .id(LECTURE_ID)
                .title(LECTURE_TITLE)
                .lecturer(LECTURER)
                .lectureDt(LocalDateTime.now())
                .capacity(LECTURE_CAPACITY)
                .currentCapacity(0)
                .build();

        // when-then
        assertThatExceptionOfType(CustomException.class)
                .isThrownBy(lecture::checkLectureDate)
                .withMessage(ErrorCode.LECTURE_ALREADY_ENDED.getMessage());

    }

    @Test
    @DisplayName("신청 후 특강 최대 인원 초과 시 신청 실패")
    void shouldFailRegistrationWhenLectureCapacityExceeded() {
        // given
        Lecture lecture = Lecture.builder()
                .id(LECTURE_ID)
                .title(LECTURE_TITLE)
                .lecturer(LECTURER)
                .lectureDt(LocalDateTime.now())
                .capacity(LECTURE_CAPACITY)
                .currentCapacity(30)
                .build();
        // when // then
        assertThatExceptionOfType(CustomException.class)
                .isThrownBy(lecture::checkCapacity)
                .withMessage(ErrorCode.EXCEEDED_REGISTRATION_LIMIT.getMessage());
    }

    @Test
    @DisplayName("잔여 신청 자리가 있는 경우 성공")
    void shouldSucceedWhenSeatsAreAvailable() {
        // given
        Lecture lecture = Lecture.builder()
                .id(LECTURE_ID)
                .title(LECTURE_TITLE)
                .lecturer(LECTURER)
                .lectureDt(LocalDateTime.now())
                .capacity(LECTURE_CAPACITY)
                .currentCapacity(0)
                .build();

        // when-then
        assertDoesNotThrow(lecture::checkCapacity);
    }

    @Test
    @DisplayName("현재 신청 인원을 증가 성공")
    void shouldIncreaseCurrentRegistrationCountSuccessfully() {
        // given
        Lecture lecture = Lecture.builder()
                .id(LECTURE_ID)
                .title(LECTURE_TITLE)
                .lecturer(LECTURER)
                .lectureDt(LocalDateTime.now())
                .capacity(LECTURE_CAPACITY)
                .currentCapacity(0)
                .build();

        // when-then
        Lecture updateLecture = lecture.increaseCapacity();
        assertThat(updateLecture.currentCapacity()).isEqualTo(lecture.currentCapacity() + 1);
        assertThat(updateLecture.capacity()).isEqualTo(lecture.capacity());
        assertThat(updateLecture.lectureDt()).isEqualTo(lecture.lectureDt());
    }

}
