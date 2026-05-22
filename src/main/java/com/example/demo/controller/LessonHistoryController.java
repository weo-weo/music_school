package com.example.demo.controller;

import com.example.demo.entity.LessonHistory;
import com.example.demo.repository.LessonHistoryRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonHistoryController {

    private final LessonHistoryRepository repository;

    public LessonHistoryController(LessonHistoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<LessonHistory> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public LessonHistory create(@RequestBody LessonHistory history) {
        return repository.save(history);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/{id}")
    public LessonHistory getOne(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public LessonHistory update(@RequestBody LessonHistory direction, @PathVariable Long id) {
        direction.setId(id);
        return repository.save(direction);
    }
}