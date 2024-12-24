package hhplus.tdd.domain.service;

import hhplus.tdd.domain.repository.RegistrationRepository;
import hhplus.tdd.global.exception.CustomException;
import hhplus.tdd.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
