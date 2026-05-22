package com.example.demo.controller;

import com.example.demo.entity.Qualification;
import com.example.demo.entity.Role;
import com.example.demo.entity.Teacher;
import com.example.demo.entity.User;
import com.example.demo.repository.QualificationRepository;
import com.example.demo.repository.TeacherRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class TeacherControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private QualificationRepository qualificationRepository;

    @Autowired
    private UserRepository userRepository;

    private Teacher savedTeacher;
    private Qualification savedQualification;
    private User teacherUser;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        Qualification qualification = new Qualification();
        qualification.setName("Высшая категория");
        qualification.setSalaryCoefficient(2.0);
        this.savedQualification = qualificationRepository.save(qualification);

        User user = new User();
        user.setUsername("mozart_user");
        user.setPassword("opera");
        user.setRole(Role.TEACHER);
        this.teacherUser = userRepository.save(user);

        Teacher teacher = new Teacher();
        teacher.setFirstName("Вольфганг");
        teacher.setLastName("Моцарт");
        teacher.setPhone("89112223344");
        teacher.setEmail("mozart@music.com");
        teacher.setQualification(savedQualification);
        teacher.setUser(teacherUser);
        this.savedTeacher = teacherRepository.save(teacher);
    }

    @Test
    void shouldGetAllTeachers() throws Exception {
        mockMvc.perform(get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Вольфганг"))
                .andExpect(jsonPath("$[0].email").value("mozart@music.com"));
    }

    @Test
    void shouldGetTeacherById() throws Exception {
        mockMvc.perform(get("/api/teachers/" + savedTeacher.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Моцарт"));
    }

    @Test
    void shouldCreateTeacher() throws Exception {
        User newUser = new User();
        newUser.setUsername("vivaldi_user");
        newUser.setPassword("opera");
        newUser.setRole(Role.TEACHER);
        newUser = userRepository.save(newUser);

        String json = String.format("""
                {
                    "firstName": "Антонио",
                    "lastName": "Вивальди",
                    "phone": "89001112233",
                    "email": "vivaldi@seasons.com",
                    "qualification": {"id": %d},
                    "user": {"id": %d}
                }
                """, savedQualification.getId(), newUser.getId());

        mockMvc.perform(post("/api/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateTeacher() throws Exception {
        String json = String.format("""
                {
                    "firstName": "Вольфганг_Обновленный",
                    "lastName": "Моцарт",
                    "phone": "89000000000",
                    "email": "mozart_new@music.com",
                    "qualification": {"id": %d}
                }
                """, savedQualification.getId());

        mockMvc.perform(put("/api/teachers/" + savedTeacher.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteTeacher() throws Exception {
        mockMvc.perform(delete("/api/teachers/" + savedTeacher.getId()))
                .andExpect(status().isOk());
    }
}