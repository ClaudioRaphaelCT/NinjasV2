package com.raphael.project.ninjas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record GlobalResponse<T>(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/YYYY HH:mm:ss")
        LocalDateTime timestamp,
        String mensagem,
        T dados
) {
}
