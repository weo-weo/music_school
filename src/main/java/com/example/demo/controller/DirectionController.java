package com.example.demo.controller;

import com.example.demo.entity.Direction;
import com.example.demo.repository.DirectionRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/directions")
public class DirectionController {

    private final DirectionRepository repository;

    public DirectionController(DirectionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Direction> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Direction create(@RequestBody Direction direction) {
        return repository.save(direction);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/{id}")
    public Direction getOne(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public Direction update(@RequestBody Direction direction, @PathVariable Long id) {
        direction.setId(id);
        return repository.save(direction);
    }
}