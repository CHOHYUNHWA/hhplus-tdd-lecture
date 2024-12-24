package hhplus.tdd.domain.service;

import hhplus.tdd.domain.model.Registration;
import hhplus.tdd.domain.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    public void checkIfLectureAlreadyRegistered(Long lectureId, Long studentId) {
    }

    public void register(Long lectureId, Long studentId) {
    }

    public List<Registration> getCompletedRegistrationLectures(long studentId) {
        return null;
    }
}
