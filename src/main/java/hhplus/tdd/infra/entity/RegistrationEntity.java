package hhplus.tdd.infra.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "REGISTRATION"
        , uniqueConstraints = {@UniqueConstraint(columnNames = {"STUDENT_ID", "LECTURE_ID"})}
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegistrationEntity {

    @Id @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 매핑
    @JoinColumn(name = "lecture_id", nullable = false) // lecture_id가 외래키
    private LectureEntity lecture;

    @Column(name = "STUDENT_ID", nullable = false)
    private Long studentId;

    @Column(name = "REGISTRATION_DT", nullable = false)
    private LocalDateTime registrationDt;

    public static RegistrationEntity of(Long lectureId, Long studentId) {
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.studentId = studentId;

        registrationEntity.lecture = new LectureEntity();
        registrationEntity.lecture.addLectureId(lectureId);
        registrationEntity.registrationDt = LocalDateTime.now();

        return registrationEntity;
    }
}
