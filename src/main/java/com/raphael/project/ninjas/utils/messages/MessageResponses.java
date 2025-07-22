package com.raphael.project.ninjas.utils.messages;

import com.raphael.project.ninjas.dto.GlobalResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageResponses {
    private final MessageGlobalResponse response;

    public <T> GlobalResponse<T> inserirSucesso(T data, Object... args) {
        return response.getMessageGlobal("ninja.inserir.sucesso", data, args);
    }

    public <T> GlobalResponse<T> buscarTodosSucesso(T data, Object... args) {
        return response.getMessageGlobal("ninja.buscar.todos.sucesso", data);
    }

    public <T> GlobalResponse<T> buscarPorIdSucesso(T data, Object... args) {
        return response.getMessageGlobal("ninja.buscar.id.sucesso", data, args);
    }

    public <T> GlobalResponse<T> deletarPorId(T data, Object... args) {
        return response.getMessageGlobal("ninja.deletar.sucesso", data, args);
    }

    public <T> GlobalResponse<T> atualizarPorId(T data, Object... args) {
        return response.getMessageGlobal("ninja.atualizar.sucesso", data, args);
    }

}
