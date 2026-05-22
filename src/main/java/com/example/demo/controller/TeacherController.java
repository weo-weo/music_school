package com.example.demo.controller;

import com.example.demo.entity.Teacher;
import com.example.demo.repository.TeacherRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherRepository repository;

    public TeacherController(TeacherRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Teacher> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Teacher create(@RequestBody Teacher teacher) {
        return repository.save(teacher);
    }

    @PutMapping("/{id}")
    public Teacher update(@RequestBody Teacher teacher, @PathVariable Long id) {
        teacher.setId(id);
        return repository.save(teacher);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/{id}")
    public Teacher getOne(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

}