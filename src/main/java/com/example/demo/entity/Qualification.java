package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "qualifications")
@Getter
@Setter
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Код квалификации

    @Column(nullable = false)
    private String name; // Наименование

    @Column(name = "payment_coefficient", nullable = false)
    private Double salaryCoefficient; // Коэффициент оплаты
}