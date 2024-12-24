package hhplus.tdd.infra.repository;

import hhplus.tdd.infra.entity.LectureCapacityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureCapacityJpaRepository extends JpaRepository<LectureCapacityEntity, Long> {
    Optional<LectureCapacityEntity> findByLectureId(Long id);
}
