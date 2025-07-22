package com.raphael.project.ninjas.utils.messages;

import com.raphael.project.ninjas.exceptions.NinjaNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageExceptionResponses {
    private final MessageGlobalResponse response;

    public NinjaNotFoundException notFoundException(String key, Object... args) {
        String message = response.getMessageArgs(key, args);
        return new NinjaNotFoundException(message);
    }
}
