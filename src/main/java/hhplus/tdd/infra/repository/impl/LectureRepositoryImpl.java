package hhplus.tdd.infra.repository.impl;

import hhplus.tdd.application.dto.LectureRegisterResponse;
import hhplus.tdd.domain.model.Lecture;
import hhplus.tdd.domain.repository.LectureRepository;
import hhplus.tdd.global.exception.CustomException;
import hhplus.tdd.global.exception.ErrorCode;
import hhplus.tdd.infra.entity.LectureCapacityEntity;
import hhplus.tdd.infra.entity.LectureEntity;
import hhplus.tdd.infra.repository.LectureCapacityJpaRepository;
import hhplus.tdd.infra.repository.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;
    private final LectureCapacityJpaRepository lectureCapacityJpaRepository;

    @Override
    public Lecture findById(Long lectureId) {

        LectureEntity lectureEntity = lectureJpaRepository.findById(lectureId).orElseThrow(() -> new CustomException(ErrorCode.LECTURE_NOT_FOUND));

        LectureCapacityEntity lectureCapacityEntity = lectureEntity.getLectureCapacity();

        if(lectureCapacityEntity == null) throw new CustomException(ErrorCode.LECTURE_CAPACITY_NOT_FOUND);

        return Lecture.builder()
                .id(lectureEntity.getId())
                .title(lectureEntity.getTitle())
                .lecturer(lectureEntity.getLecturer())
                .lectureDt(lectureEntity.getLectureDt())
                .currentCapacity(lectureCapacityEntity.getCurrentCount())
                .capacity(lectureCapacityEntity.getCapacity())
                .build();
    }

    @Override
    public List<Lecture> findAvailableLectureByLectureDt(LocalDate lectureDate) {
        LocalDateTime startOfDay = lectureDate.atStartOfDay();
        LocalDateTime endOfDay = lectureDate.atTime(23, 59, 59);

        return lectureJpaRepository.findByLectureDt(startOfDay, endOfDay)
                .stream()
                .map(entity -> Lecture.builder()
                        .id(entity.getId())
                        .title(entity.getTitle())
                        .lecturer(entity.getLecturer())
                        .lectureDt(entity.getLectureDt())
                        .capacity(entity.getLectureCapacity().getCapacity())
                        .currentCapacity(entity.getLectureCapacity().getCurrentCount())
                        .build())
                .toList();
    }

    @Override
    public void increaseCurrentCapacity(Lecture lecture) {
        LectureCapacityEntity lectureCapacityEntity = lectureCapacityJpaRepository.findByLectureId(lecture.id())
                .orElseThrow(() -> new CustomException(ErrorCode.LECTURE_CAPACITY_NOT_FOUND));
        lectureCapacityEntity.increaseCurrentCapacity(lecture.currentCapacity());

        lectureCapacityJpaRepository.save(lectureCapacityEntity);
    }
}
