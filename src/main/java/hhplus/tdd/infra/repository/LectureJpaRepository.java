package hhplus.tdd.infra.repository;

import hhplus.tdd.infra.entity.LectureEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureJpaRepository extends JpaRepository<LectureEntity, Long> {

    @Query("SELECT l FROM LectureEntity l JOIN l.lectureCapacity c WHERE l.lectureDt >= :startDateTime AND l.lectureDt <= :endDateTime AND c.currentCount < c.capacity")
    List<LectureEntity> findByLectureDt(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LectureEntity> findById(Long lectureId);

}
