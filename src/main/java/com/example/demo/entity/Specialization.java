package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "specializations")
@Getter
@Setter
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Код специализации

    // Какой преподаватель...
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    // ...какое направление ведет
    @ManyToOne
    @JoinColumn(name = "direction_id", nullable = false)
    private Direction direction;
}