package hhplus.tdd.domain.service;

import hhplus.tdd.domain.model.Lecture;
import hhplus.tdd.domain.repository.LectureRepository;
import hhplus.tdd.global.exception.CustomException;
import hhplus.tdd.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private final Long LECTURE_ID = 1L;


    @Test
    @DisplayName("조회한 특강이 존재하지 않을 경우 실패")
    void shouldFailWhenLectureDoesNotExist() {
        // given
        when(lectureRepository.findById(LECTURE_ID)).thenThrow(new CustomException(ErrorCode.LECTURE_NOT_FOUND));
        // when then
        assertThatThrownBy(() -> lectureService.checkLectureStatus(LECTURE_ID))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.LECTURE_NOT_FOUND.getMessage());

        verify(lectureRepository).findById(LECTURE_ID);
    }

    @Test
    @DisplayName("특정 특강 조회 잔여 좌석 조회 성공")
    void shouldRetrieveOpenedLecturesSuccessfully() {
        // given
        Lecture mockLecture = mock(Lecture.class);
        when(mockLecture.id()).thenReturn(LECTURE_ID);
        when(mockLecture.title()).thenReturn("Good Lecture");
        when(mockLecture.lecturer()).thenReturn("Mr,James");
        when(mockLecture.currentCapacity()).thenReturn(0);
        when(mockLecture.capacity()).thenReturn(30);
        doNothing().when(mockLecture).checkLectureDate();
        doNothing().when(mockLecture).checkCapacity();

        when(lectureRepository.findById(LECTURE_ID)).thenReturn(mockLecture);
        // when
        Lecture lecture = lectureService.checkLectureStatus(LECTURE_ID);

        // then
        assertThat(lecture).isNotNull();
        assertThat(lecture.id()).isEqualTo(LECTURE_ID);
        assertThat(lecture.title()).isEqualTo("Good Lecture");

        verify(lectureRepository).findById(LECTURE_ID);
    }

    @Test
    @DisplayName("특정 특강 좌석 초과로 조회 실패")
    void shouldFailWhenLectureCapacityExceeded() {
        // given
        Lecture mockLecture = mock(Lecture.class);
        when(lectureRepository.findById(LECTURE_ID)).thenReturn(mockLecture);

        doThrow(new CustomException(ErrorCode.EXCEEDED_REGISTRATION_LIMIT))
                .when(mockLecture).checkCapacity();
        // when
        CustomException exception = assertThrows(CustomException.class, () -> lectureService.checkLectureStatus(LECTURE_ID));

        // then
        assertEquals(ErrorCode.EXCEEDED_REGISTRATION_LIMIT, exception.getErrorCode());
        verify(lectureRepository).findById(LECTURE_ID);

    }


    @Test
    @DisplayName("특강일 보다 신청일이 이후인 경우 조회 실패")
    void shouldFailWhenRegistrationDateIsAfterLectureDate() {
        // given
        Lecture mockLecture = mock(Lecture.class);
        when(lectureRepository.findById(LECTURE_ID)).thenReturn(mockLecture);
        doThrow(new CustomException(ErrorCode.LECTURE_ALREADY_ENDED))
                .when(mockLecture).checkLectureDate();

        //when
        CustomException exception = assertThrows(CustomException.class, () -> lectureService.checkLectureStatus(LECTURE_ID));

        // then
        assertEquals(ErrorCode.LECTURE_ALREADY_ENDED, exception.getErrorCode());
        verify(mockLecture, never()).checkCapacity();
    }

    @Test
    @DisplayName("신청 가능한 특강 조회에 성공")
    void shouldRetrieveAvailableLecturesSuccessfully() {
        // given
        LocalDate lectureDate = LocalDate.now().plusDays(5);
        List<Lecture> lectures = List.of(mock(Lecture.class),mock(Lecture.class),mock(Lecture.class));
        when(lectureRepository.findAvailableLectureByLectureDt(lectureDate)).thenReturn(lectures);
        // when
        List<Lecture> findByLectures = lectureService.getAvailableLectures(lectureDate);

        // then
        assertThat(findByLectures).isNotNull();
        assertThat(findByLectures.size()).isEqualTo(lectures.size());
        verify(lectureRepository).findAvailableLectureByLectureDt(lectureDate);
    }

    @Test
    @DisplayName("현재 특강의 신청에 성공하여 신청 인원 증가 성공")
    void shouldIncreaseRegistrationCountSuccessfullyAfterSuccessfulApplication() {
        // given
        Lecture mockLecture = mock(Lecture.class);
        Lecture updateMockLecture = mock(Lecture.class);

        when(mockLecture.increaseCapacity()).thenReturn(updateMockLecture);
        // when
        lectureService.incrementCapacity(mockLecture);

        // then
        verify(mockLecture).increaseCapacity();
        verify(lectureRepository).increaseCurrentCapacity(updateMockLecture);
    }

}
