package hhplus.tdd.domain.service;

import hhplus.tdd.domain.repository.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}
