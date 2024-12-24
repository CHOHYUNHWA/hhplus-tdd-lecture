package hhplus.tdd.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import hhplus.tdd.global.exception.CustomException;
import hhplus.tdd.global.exception.ErrorCode;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Lecture (
    Long id,
    Long registrationId,
    Long studentId,
    String title,
    String lecturer,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime lectureDt,
    Integer capacity,
    Integer currentCapacity
){

    public void checkLectureDate() {
        if(LocalDateTime.now().isAfter(this.lectureDt)) {
            throw new CustomException(ErrorCode.LECTURE_ALREADY_ENDED);
        }
    }

    public void checkCapacity() {
        if(this.currentCapacity >= this.capacity) {
            throw new CustomException(ErrorCode.EXCEEDED_REGISTRATION_LIMIT);
        }
    }

    public Lecture increaseCapacity() {
        return Lecture.builder()
                .id(this.id)
                .title(this.title)
                .lecturer(this.lecturer)
                .capacity(this.capacity)
                .currentCapacity(this.currentCapacity + 1)
                .lectureDt(this.lectureDt)
                .build();
    }
}
