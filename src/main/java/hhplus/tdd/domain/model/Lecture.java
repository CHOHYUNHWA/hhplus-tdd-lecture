package hhplus.tdd.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

}
