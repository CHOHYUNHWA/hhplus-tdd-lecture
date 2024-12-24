package hhplus.tdd.infra.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "LECTURE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureEntity {

    @Id @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "TITLE", nullable = false)
    private String name;

    @Column(name = "LECTURER", nullable = false)
    private String lecturer;

    @Column(name = "LECTURE_DT", nullable = false)
    private LocalDateTime lectureDt;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "lectureId")
    private LectureCapacityEntity lectureCapacity;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegistrationEntity> registrationList;

    public void addLectureId(long id){
        this.id = id;
    }
}
