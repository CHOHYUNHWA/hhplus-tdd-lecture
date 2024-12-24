package hhplus.tdd.infra.repository.impl;

import hhplus.tdd.domain.repository.RegistrationRepository;
import hhplus.tdd.infra.entity.RegistrationEntity;
import hhplus.tdd.infra.repository.RegistrationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RegistrationRepositoryImpl implements RegistrationRepository {

    private final RegistrationJpaRepository registrationJpaRepository;


    @Override
    public boolean checkIfLectureAlreadyRegistered(Long lectureId, Long studentId) {
        Optional<RegistrationEntity> registration = registrationJpaRepository.findByLectureIdAndStudentId(lectureId, studentId);
        return registration.isPresent();
    }

    @Override
    public void save(Long lectureId, Long studentId) {
        RegistrationEntity registrationEntity = RegistrationEntity.of(lectureId, studentId);
        registrationJpaRepository.save(registrationEntity);
    }
}
