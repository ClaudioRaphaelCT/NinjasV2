package com.raphael.project.ninjas.controller;

import com.raphael.project.ninjas.dto.GlobalResponse;
import com.raphael.project.ninjas.dto.NinjaRequest;
import com.raphael.project.ninjas.dto.NinjaResponse;
import com.raphael.project.ninjas.service.NinjaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/ninjas")
@AllArgsConstructor
public class NinjaController {
    private final NinjaService service;

    @PostMapping
    public ResponseEntity<GlobalResponse<NinjaResponse>> inserir(@Valid @RequestBody NinjaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.inserir(request));
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<NinjaResponse>>> buscarTodos() {
        return ResponseEntity.ok().body(service.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<NinjaResponse>> buscarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse<NinjaResponse>> atualizarPorId(
            @PathVariable("id") Long id, @Valid @RequestBody NinjaRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.atualizarPorId(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<NinjaResponse>> deletarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.deletarPorId(id));
    }


}
