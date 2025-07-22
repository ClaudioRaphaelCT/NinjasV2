package com.raphael.project.ninjas.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class NinjaNotFoundException extends RuntimeException {
    public NinjaNotFoundException(String message) {
        super(message);
    }
}
