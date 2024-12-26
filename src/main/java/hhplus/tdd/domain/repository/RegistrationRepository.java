package hhplus.tdd.domain.repository;

import hhplus.tdd.domain.model.Registration;

import java.util.List;

public interface RegistrationRepository {

    boolean checkIfLectureAlreadyRegistered(Long lectureId, Long studentId);

    void save(Long lectureId, Long studentId);

    List<Registration> findByStudentId(long studentId);
}
