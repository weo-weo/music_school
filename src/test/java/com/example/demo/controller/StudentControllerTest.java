package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class StudentControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    private Student savedStudent;
    private User studentUser;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        User user = new User();
        user.setUsername("student_user");
        user.setPassword("pass");
        user.setRole(Role.STUDENT);
        this.studentUser = userRepository.save(user);

        Student student = new Student();
        student.setFirstName("Петр");
        student.setLastName("Чайковский");
        student.setBirthDate(LocalDate.of(2015, 5, 7));
        student.setPhone("89991112233");
        student.setGender("Мужской");
        student.setUser(studentUser);
        this.savedStudent = studentRepository.save(student);
    }

    @Test
    void shouldGetAllStudents() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Петр"))
                .andExpect(jsonPath("$[0].lastName").value("Чайковский"));
    }

    @Test
    void shouldGetStudentById() throws Exception {
        mockMvc.perform(get("/api/students/" + savedStudent.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Петр"));
    }

    @Test
    void shouldCreateStudent() throws Exception {
        User newUser = new User();
        newUser.setUsername("another_student_user");
        newUser.setPassword("pass");
        newUser.setRole(Role.STUDENT);
        newUser = userRepository.save(newUser);

        String json = String.format("""
                {
                    "firstName": "Ани Лорак",
                    "lastName": "Певица",
                    "birthDate": "2012-09-12",
                    "phone": "89995554433",
                    "gender": "Женский",
                    "user": {"id": %d}
                }
                """, newUser.getId());

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateStudent() throws Exception {
        String json = String.format("""
                {
                    "firstName": "Петр_Обновленный",
                    "lastName": "Чайковский",
                    "phone": "89990000000",
                    "user": {"id": %d}
                }
                """, studentUser.getId());

        mockMvc.perform(put("/api/students/" + savedStudent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteStudent() throws Exception {
        mockMvc.perform(delete("/api/students/" + savedStudent.getId()))
                .andExpect(status().isOk());
    }
}