package hhplus.tdd.domain.service;

import hhplus.tdd.domain.model.Registration;
import hhplus.tdd.domain.repository.RegistrationRepository;
import hhplus.tdd.global.exception.CustomException;
import hhplus.tdd.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static Long LECTURE_ID = 1L;
    private static Long STUDENT_ID = 1L;


    @Test
    @DisplayName("강의가 중복 신청일 경우 예외 발생")
    void shouldThrowExceptionWhenLectureIsAlreadyRegistered() {
        //given
        when(registrationRepository.checkIfLectureAlreadyRegistered(LECTURE_ID, STUDENT_ID)).thenReturn(true);

        //when
        CustomException exception = assertThrows(CustomException.class, () -> registrationService.checkIfLectureAlreadyRegistered(LECTURE_ID, STUDENT_ID));

        //then
        assertEquals(ErrorCode.ALREADY_REGISTERED, exception.getErrorCode());
        verify(registrationRepository).checkIfLectureAlreadyRegistered(LECTURE_ID, STUDENT_ID);
    }

    @Test
    @DisplayName("강의가 중복 신청이 아닌경우 통과")
    void passWhenLectureIsNotRegistered(){
        //given
        when(registrationRepository.checkIfLectureAlreadyRegistered(LECTURE_ID, STUDENT_ID)).thenReturn(false);

        //when
        assertDoesNotThrow(() -> registrationService.checkIfLectureAlreadyRegistered(LECTURE_ID, STUDENT_ID));

        //then
        verify(registrationRepository).checkIfLectureAlreadyRegistered(LECTURE_ID, STUDENT_ID);

    }

    @Test
    @DisplayName("정상적으로 특강 신청이 완료된다.")
    void completeLectureRegistrationSuccessfully(){
        //given
        doNothing().when(registrationRepository).save(LECTURE_ID, STUDENT_ID);

        //when
        assertDoesNotThrow(() -> registrationService.register(LECTURE_ID, STUDENT_ID));

        //then
        verify(registrationRepository).save(LECTURE_ID, STUDENT_ID);
    }

    @Test
    @DisplayName("정상적으로 특강 신청 조회가 된다.")
    void shouldRetrieveLectureRegistrationSuccessfully(){
        //given
        List<Registration> mockRegistrationResponse = List.of(mock(Registration.class));
        when(registrationRepository.findByStudentId(STUDENT_ID)).thenReturn(mockRegistrationResponse);

        //when
        assertDoesNotThrow(() -> registrationService.getCompletedRegistrationLectures(STUDENT_ID));

        //then
        verify(registrationRepository).findByStudentId(STUDENT_ID);
    }

}
