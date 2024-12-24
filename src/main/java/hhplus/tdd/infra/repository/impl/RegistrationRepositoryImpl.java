package hhplus.tdd.infra.repository.impl;

import hhplus.tdd.domain.model.Registration;
import hhplus.tdd.domain.repository.RegistrationRepository;
import hhplus.tdd.infra.entity.LectureEntity;
import hhplus.tdd.infra.entity.RegistrationEntity;
import hhplus.tdd.infra.repository.RegistrationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public List<Registration> findByStudentId(long studentId) {


        return registrationJpaRepository.findByStudentId(studentId).stream()
                .map(entity -> {

                    LectureEntity lectureEntity = entity.getLecture();
                    return Registration.builder()
                            .id(entity.getId())
                            .lectureId(lectureEntity.getId())
                            .studentId(entity.getStudentId())
                            .title(lectureEntity.getTitle())
                            .lecturer(lectureEntity.getLecturer())
                            .registrationDt(entity.getRegistrationDt())
                            .lectureDt(lectureEntity.getLectureDt())
                            .build();
                })
                .toList();
    }
}
