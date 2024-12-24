package hhplus.tdd.domain.service;

import hhplus.tdd.domain.model.Registration;
import hhplus.tdd.domain.repository.RegistrationRepository;
import hhplus.tdd.global.exception.CustomException;
import hhplus.tdd.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    public void checkIfLectureAlreadyRegistered(Long lectureId, Long studentId) {
        boolean isRegistered = registrationRepository.checkIfLectureAlreadyRegistered(lectureId, studentId);

        if(isRegistered){
            throw new CustomException(ErrorCode.ALREADY_REGISTERED);
        }
    }

    public void register(Long lectureId, Long studentId) {

    }

    public List<Registration> getCompletedRegistrationLectures(long studentId) {
        return null;
    }
}
