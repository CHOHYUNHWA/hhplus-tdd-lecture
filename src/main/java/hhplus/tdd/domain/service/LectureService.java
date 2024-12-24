package hhplus.tdd.domain.service;

import hhplus.tdd.application.dto.LectureRegisterResponse;
import hhplus.tdd.domain.model.Lecture;
import hhplus.tdd.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    public Lecture validateAndGetLectureForRegistration(Long lectureId) {
        return null;
    }

    public LectureRegisterResponse incrementCapacity(Lecture lecture) {
        return null;
    }

    public List<Lecture> getAvailableLectures(LocalDateTime lectureDate) {
        return null;
    }
}
