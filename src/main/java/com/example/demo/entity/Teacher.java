package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "teachers")
@Getter
@Setter
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Код преподавателя

    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String email;

    // Связь с квалификацией (Много преподавателей могут иметь одну квалификацию)
    @ManyToOne
    @JoinColumn(name = "qualification_id", nullable = false)
    private Qualification qualification;

    // Связь с аккаунтом для входа
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}