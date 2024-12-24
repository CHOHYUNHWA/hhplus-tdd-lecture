package hhplus.tdd.domain.service;

import hhplus.tdd.application.dto.LectureRegisterResponse;
import hhplus.tdd.domain.model.Lecture;
import hhplus.tdd.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    //해당 특강신청이 가능한 상태인지 확인 하고 신청이 가능한 상태인 경우 Lecture 을 반환한다.
    public Lecture checkLectureStatus(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId);
        lecture.checkLectureDate();
        lecture.checkCapacity();
        return lecture;
    }

    //총 강의 신청 수를 +1 시킨다.
    public void incrementCapacity(Lecture lecture) {

        Lecture updateLecture = lecture.increaseCapacity();

        lectureRepository.increaseCurrentCapacity(updateLecture);
    }

    //특정 날짜 기준으로 신청 가능한 특강을 보여준다.
    public List<Lecture> getAvailableLectures(LocalDate lectureDate) {
        return lectureRepository.findAvailableLectureByLectureDt(lectureDate);
    }
}
