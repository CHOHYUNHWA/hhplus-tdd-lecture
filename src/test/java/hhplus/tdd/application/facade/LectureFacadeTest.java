package hhplus.tdd.application.facade;

import hhplus.tdd.application.dto.AvailableLectureResponse;
import hhplus.tdd.application.dto.CompletedRegistrationLecturesResponse;
import hhplus.tdd.application.dto.RegistrationRequest;
import hhplus.tdd.domain.model.Lecture;
import hhplus.tdd.domain.model.Registration;
import hhplus.tdd.domain.service.LectureService;
import hhplus.tdd.domain.service.RegistrationService;
import hhplus.tdd.global.exception.CustomException;
import hhplus.tdd.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LectureFacadeTest {

    @InjectMocks
    private LectureFacade lectureFacade;

    @Mock
    private LectureService lectureService;

    @Mock
    private RegistrationService registrationService;

    private final Long LECTURE_ID = 1L;
    private final Long STUDENT_ID = 1L;
    private RegistrationRequest registrationRequest;
    private Lecture lecture;

    @BeforeEach
    void setUp(){
        registrationRequest = new RegistrationRequest(LECTURE_ID, STUDENT_ID);

        lecture = Lecture.builder()
                .id(LECTURE_ID)
                .title("JPA")
                .lecturer("JPA 달인")
                .lectureDt(LocalDateTime.now())
                .build();
    }

    @Test
    void 이미_신청한_학생이_신청_시_실패(){
        //given
        doThrow(new CustomException(ErrorCode.ALREADY_REGISTERED))
                .when(registrationService).checkIfLectureAlreadyRegistered(LECTURE_ID, STUDENT_ID);
        //when //then
        assertThatThrownBy(() -> lectureFacade.registerLecture(registrationRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.ALREADY_REGISTERED.getMessage());

        verify(lectureService , never()).checkLectureStatus(LECTURE_ID);
        verify(registrationService, never()).register(LECTURE_ID, STUDENT_ID);
        verify(lectureService, never()).incrementCapacity(lecture);
    }

    @Test
    void 최대_신청인원_초과_시_실패(){
        //given
        doThrow(new CustomException(ErrorCode.EXCEEDED_REGISTRATION_LIMIT))
                .when(lectureService).checkLectureStatus(LECTURE_ID);
        //when then
        assertThatThrownBy(() -> lectureFacade.registerLecture(registrationRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.EXCEEDED_REGISTRATION_LIMIT.getMessage());

        verify(registrationService).checkIfLectureAlreadyRegistered(LECTURE_ID, STUDENT_ID);
        verify(registrationService,never()).register(LECTURE_ID, STUDENT_ID);
        verify(lectureService,never()).incrementCapacity(lecture);
    }

    @Test
    void 특강_신청_시_신청인원이_초과하지_않고_이미_신청한_학생이_아닌_경우_성공(){
        //given
        doNothing().when(registrationService).checkIfLectureAlreadyRegistered(LECTURE_ID, STUDENT_ID);
        when(lectureService.checkLectureStatus(LECTURE_ID)).thenReturn(lecture);
        doNothing().when(registrationService).register(LECTURE_ID, STUDENT_ID);
        doNothing().when(lectureService).incrementCapacity(lecture);

        //when
        //then
        assertThatCode(() -> lectureFacade.registerLecture(registrationRequest)).doesNotThrowAnyException();

        verify(registrationService).checkIfLectureAlreadyRegistered(LECTURE_ID, STUDENT_ID);
        verify(lectureService).checkLectureStatus(LECTURE_ID);
        verify(registrationService).register(LECTURE_ID, STUDENT_ID);
        verify(lectureService).incrementCapacity(lecture);
    }

    @Test
    void 날짜로_특강_검색_시_신청_가능한_특강_조회_성공(){
        //given
        LocalDate lectureSearchDate = LocalDate.now();
        List<Lecture> mockLectures = List.of(mock(Lecture.class));
        when(lectureService.getAvailableLectures(lectureSearchDate)).thenReturn(mockLectures);
        //when

        AvailableLectureResponse availableLectureResponse = lectureFacade.getAvailableLectures(lectureSearchDate);

        //then
        assertThat(availableLectureResponse).isNotNull();
        assertThat(availableLectureResponse.lectures()).hasSize(1);
        verify(lectureService).getAvailableLectures(lectureSearchDate);
    }

    @Test
    void 특정_학생이_신청한_특강_조회_성공(){
        //given
        List<Registration> mockRegistrations = List.of(mock(Registration.class));
        when(registrationService.getCompletedRegistrationLectures(STUDENT_ID)).thenReturn(mockRegistrations);
        //when

        CompletedRegistrationLecturesResponse completedRegistrationLecturesResponse = lectureFacade.getCompletedRegistrationLectures(STUDENT_ID);
        //then

        assertThat(completedRegistrationLecturesResponse).isNotNull();
        assertThat(completedRegistrationLecturesResponse.registrations()).hasSize(1);
        verify(registrationService).getCompletedRegistrationLectures(STUDENT_ID);
    }


}
