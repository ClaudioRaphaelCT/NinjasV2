package com.raphael.project.ninjas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record NinjaResponse(
        Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        @JsonProperty("data_criacao")
        LocalDateTime createdAt,
        String nome,
        String vila,
        String rank,
        Double poder
) {
}
