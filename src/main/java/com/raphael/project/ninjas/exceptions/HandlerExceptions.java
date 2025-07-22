package com.raphael.project.ninjas.exceptions;

import com.raphael.project.ninjas.dto.ExceptionResponse;
import com.raphael.project.ninjas.utils.messages.MessageGlobalResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
public class HandlerExceptions {
    private final MessageGlobalResponse messageGlobalResponse;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handlerNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String erroMessage = messageGlobalResponse.getMessage(error.getDefaultMessage());
            erros.put(error.getField(), erroMessage);
        });
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                "Bad request",
                erros
        );
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(NinjaNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlerNotFoundException(NinjaNotFoundException ex) {
        ExceptionResponse notFoundResponse = new ExceptionResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
    }
}
