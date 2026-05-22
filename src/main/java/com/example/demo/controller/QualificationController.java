package com.example.demo.controller;

import com.example.demo.entity.Qualification;
import com.example.demo.repository.QualificationRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/qualifications")
public class QualificationController {

    private final QualificationRepository repository;

    public QualificationController(QualificationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Qualification> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Qualification create(@RequestBody Qualification qualification) {
        return repository.save(qualification);
    }

    @GetMapping("/{id}")
    public Qualification getOne(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public Qualification update(@RequestBody Qualification qualification, @PathVariable Long id) {
        qualification.setId(id);
        return repository.save(qualification);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}