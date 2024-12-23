package hhplus.tdd.infra.repository;

import hhplus.tdd.infra.entity.LectureCapacityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureCapacityJpaRepository extends JpaRepository<LectureCapacityEntity, Long> {
}
