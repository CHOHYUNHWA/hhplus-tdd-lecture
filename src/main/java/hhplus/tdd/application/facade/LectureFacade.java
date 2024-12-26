package hhplus.tdd.application.facade;

import hhplus.tdd.application.dto.AvailableLectureResponse;
import hhplus.tdd.application.dto.CompletedRegistrationLecturesResponse;
import hhplus.tdd.application.dto.LectureRegisterResponse;
import hhplus.tdd.application.dto.RegistrationRequest;
import hhplus.tdd.domain.model.Lecture;
import hhplus.tdd.domain.model.Registration;
import hhplus.tdd.domain.service.LectureService;
import hhplus.tdd.domain.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureFacade {

    private final LectureService lectureService;
    private final RegistrationService registrationService;


    //강의 등록
    @Transactional
    public void registerLecture(RegistrationRequest registrationRequest) {

        //수강생이 이미 신청한 강의인치 체크
        registrationService.checkIfLectureAlreadyRegistered(registrationRequest.lectureId(), registrationRequest.studentId());

        //강의가 수강신청이 가능한지 확인 후
        Lecture lecture = lectureService.checkLectureStatus(registrationRequest.lectureId());

        //강의 수강신청
        registrationService.register(registrationRequest.lectureId(), registrationRequest.studentId());

        //강의 신청 수 +1
        lectureService.incrementCapacity(lecture);
    }

    //날짜로 신청가능한 강의 조회
    public AvailableLectureResponse getAvailableLectures(LocalDate lectureDate) {
        List<Lecture> lectures = lectureService.getAvailableLectures(lectureDate);
        return AvailableLectureResponse.builder().lectures(lectures).build();
    }

    //신청 완료한 강의 조회
    public CompletedRegistrationLecturesResponse getCompletedRegistrationLectures(long studentId) {
        List<Registration> registrations = registrationService.getCompletedRegistrationLectures(studentId);
        return CompletedRegistrationLecturesResponse.builder().registrations(registrations).build();
    }

}
