package hhplus.tdd.domain.repository;

public interface RegistrationRepository {

    boolean checkIfLectureAlreadyRegistered(Long lectureId, Long studentId);

    void save(Long lectureId, Long studentId);
}
