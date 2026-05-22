package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "lesson_histories")
@Getter
@Setter
public class LessonHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Код записи

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = false)
    private Specialization specialization;

    @Column(nullable = false)
    private LocalDate lessonDate; // Дата занятия

    @Column(nullable = false)
    private LocalTime lessonTime; // Время занятия

    @Column(nullable = false)
    private Integer durationMinutes; // Длительность (в минутах)

    private String notes; // Примечание
}