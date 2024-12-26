package hhplus.tdd.application.dto;

import hhplus.tdd.domain.model.Lecture;
import hhplus.tdd.domain.model.Registration;
import lombok.Builder;

import java.util.List;

@Builder
public record AvailableLectureResponse (
        List<Lecture> lectures
){
}
