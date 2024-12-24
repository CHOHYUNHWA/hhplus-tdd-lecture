package hhplus.tdd.infra.repository;

import hhplus.tdd.infra.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationJpaRepository extends JpaRepository<RegistrationEntity, Long> {

    Optional<RegistrationEntity> findByLectureIdAndStudentId(Long lectureId, Long studentId);
}

