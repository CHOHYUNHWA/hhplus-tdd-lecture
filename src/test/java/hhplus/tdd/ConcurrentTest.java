package hhplus.tdd;

import hhplus.tdd.application.dto.RegistrationRequest;
import hhplus.tdd.application.facade.LectureFacade;
import hhplus.tdd.domain.model.Lecture;
import hhplus.tdd.domain.repository.LectureRepository;
import hhplus.tdd.domain.repository.RegistrationRepository;
import hhplus.tdd.domain.service.LectureService;
import hhplus.tdd.domain.service.RegistrationService;
import hhplus.tdd.infra.repository.LectureCapacityJpaRepository;
import hhplus.tdd.infra.repository.LectureJpaRepository;
import hhplus.tdd.infra.repository.RegistrationJpaRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ConcurrentTest {

    @Autowired
    private LectureFacade lectureFacade;
    @Autowired
    private LectureService lectureService;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private LectureJpaRepository lectureJpaRepository;
    @Autowired
    private LectureCapacityJpaRepository lectureCapacityJpaRepository;
    @Autowired
    private RegistrationJpaRepository registrationJpaRepository;

    private static final Logger log = LoggerFactory.getLogger(ConcurrentTest.class);

    @Test
    void 동시에_40명이_특강을_신청할_때_30명만_성공() throws InterruptedException {
        Long lectureId = 1L;

        int threadCount = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for(long i = 0; i <= threadCount; i++){
            long studentId = i+1L;
            executorService.submit(() -> {
                try {
                    RegistrationRequest registrationRequest = new RegistrationRequest(studentId, lectureId);
                    lectureFacade.registerLecture(registrationRequest);

                } catch (Exception e) {
                    log.error(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        Lecture lecture = lectureRepository.findById(lectureId);
        log.info("lecture = {}", lecture);
        assertThat(lecture.currentCapacity()).isEqualTo(30);
    }


}
