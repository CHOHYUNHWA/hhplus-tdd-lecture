package hhplus.tdd.api.web;

import hhplus.tdd.application.dto.AvailableLectureResponse;
import hhplus.tdd.application.dto.CompletedRegistrationLecturesResponse;
import hhplus.tdd.application.dto.RegistrationRequest;
import hhplus.tdd.application.facade.LectureFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@Slf4j
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;

    /**
     * 1️⃣ 특강 신청 API
     * 특정 userId로 선착순으로 제공되는 특강을 신청합니다.
     * - 동일 사용자는 동일 강의에 대해 한 번만 신청 가능
     * - 정원이 30명을 초과하면 실패
     */
    @PostMapping("/register")
    public HttpStatus applyForLecture(
            @RequestBody RegistrationRequest registrationRequest
            ) {
        return HttpStatus.CREATED;
    }

    /**
     * 2️⃣ 특강 신청 가능 목록 API
     * 날짜별로 현재 신청 가능한 특강 목록을 조회합니다.
     * - 정원은 30명으로 고정
     * - 사용자가 신청 가능한 특강 목록 조회
     */
    @GetMapping
    public ResponseEntity<AvailableLectureResponse> getAvailableLectures(
            @RequestParam LocalDate date
    ) {
        AvailableLectureResponse availableLectureResponse = lectureFacade.getAvailableLectures(date);
        return new ResponseEntity<>(availableLectureResponse, HttpStatus.OK);
    }

    /**
     * 3️⃣ 특정 유저 특강 신청 완료 목록 조회 API
     * 특정 userId로 신청 완료된 특강 목록을 조회합니다.
     * - 각 항목은 특강 ID, 이름, 강연자 정보를 포함
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<Void> getCompletedRegistrationLectures(
            @PathVariable("studentId") long studentId
    ) {
        CompletedRegistrationLecturesResponse completedRegistrationLecturesResponse = lectureFacade.getCompletedRegistrationLectures(studentId);
        return null;
    }
}