package hhplus.tdd.infra.repository.impl;

import hhplus.tdd.domain.repository.LectureRepository;
import hhplus.tdd.infra.repository.LectureCapacityJpaRepository;
import hhplus.tdd.infra.repository.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;
    private final LectureCapacityJpaRepository lectureCapacityJpaRepository;

}
