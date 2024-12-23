package hhplus.tdd.application.facade;

import hhplus.tdd.domain.repository.LectureRepository;
import hhplus.tdd.domain.service.LectureService;
import hhplus.tdd.domain.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureFacade {

    private final LectureService lectureService;
    private final RegistrationService registrationService;

}
