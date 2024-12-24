package hhplus.tdd.infra.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LECTURE_CAPACITY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureCapacityEntity {
    @Id @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LECTURE_ID", nullable = false)
    private Long lectureId;

    @Column(name = "CAPACITY", nullable = false)
    private int capacity;

    @Column(name = "CURRENT_COUNT", nullable = false)
    private int currentCount;

    public void increaseCurrentCapacity(Integer currentCapacity) {
        this.currentCount = currentCapacity;
    }
}
