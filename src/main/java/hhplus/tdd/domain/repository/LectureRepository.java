package hhplus.tdd.domain.repository;

import hhplus.tdd.domain.model.Lecture;

import java.time.LocalDate;
import java.util.List;

public interface LectureRepository {
    Lecture findById(Long lectureId);

    List<Lecture> findAvailableLectureByLectureDt(LocalDate lectureDate);

    void increaseCurrentCapacity(Lecture updateLecture);
}
