package hhplus.tdd.application.facade;

import hhplus.tdd.application.dto.AvailableLectureResponse;
import hhplus.tdd.application.dto.CompletedRegistrationLecturesResponse;
import hhplus.tdd.application.dto.LectureRegisterResponse;
import hhplus.tdd.application.dto.RegistrationRequest;
import hhplus.tdd.domain.repository.LectureRepository;
import hhplus.tdd.domain.service.LectureService;
import hhplus.tdd.domain.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LectureFacade {

    private final LectureService lectureService;
    private final RegistrationService registrationService;

    public LectureRegisterResponse registerLecture(RegistrationRequest registrationRequest) {

        return null;
    }

    public AvailableLectureResponse getAvailableLectures(LocalDateTime date) {

        return null;
    }

    public CompletedRegistrationLecturesResponse getCompletedRegistrationLectures(long studentId) {

        return null;
    }
}
