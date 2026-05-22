package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "directions")
@Getter
@Setter
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Код направления

    @Column(nullable = false)
    private String name; // Наименование

    @Column(nullable = false)
    private Double basePricePerHalfHour; // Базовая стоимость за 30 минут
}