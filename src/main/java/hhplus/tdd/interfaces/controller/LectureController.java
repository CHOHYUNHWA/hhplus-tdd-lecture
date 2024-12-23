package hhplus.tdd.interfaces.controller;

import hhplus.tdd.application.facade.LectureFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;

}
