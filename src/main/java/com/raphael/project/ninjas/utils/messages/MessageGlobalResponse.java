package com.raphael.project.ninjas.utils.messages;

import com.raphael.project.ninjas.dto.GlobalResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
@AllArgsConstructor
public class MessageGlobalResponse {
    private final MessageSource messageSource;
    private final Locale locale = LocaleContextHolder.getLocale();

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, locale);
    }

    public String getMessageArgs(String key, Object... args) {
        return messageSource.getMessage(key, args, locale);
    }

    public <T> GlobalResponse<T> getMessageGlobal(String key, T data, Object... args) {
        String mensagem = messageSource.getMessage(
                key,
                args,
                locale
        );
        return new GlobalResponse<>(
                LocalDateTime.now(),
                mensagem,
                data
        );
    }
}
