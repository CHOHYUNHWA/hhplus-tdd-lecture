package hhplus.tdd.infra.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "REGISTRATION")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegistrationEntity {

    @Id @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LECTURE_ID",nullable = false)
    private Long lectureId;

    @Column(name = "STUDENT_ID", nullable = false)
    private Long studentId;

    @Column(name = "REGISTRATION_DT", nullable = false)
    private LocalDateTime registrationDt;

}
