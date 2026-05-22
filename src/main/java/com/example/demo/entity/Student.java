package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Код ученика

    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate; // Дата рождения
    private String phone;
    private LocalDate registrationDate; // Дата регистрации
    private String gender; // Пол

    // Связь с аккаунтом для входа
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}