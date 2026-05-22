package com.example.demo.controller;

import com.example.demo.entity.Specialization;
import com.example.demo.repository.SpecializationRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {

    private final SpecializationRepository repository;

    public SpecializationController(SpecializationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Specialization> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Specialization create(@RequestBody Specialization specialization) {
        return repository.save(specialization);
    }

    @GetMapping("/{id}")
    public Specialization getOne(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public Specialization update(@RequestBody Specialization direction, @PathVariable Long id) {
        direction.setId(id);
        return repository.save(direction);
    }
}