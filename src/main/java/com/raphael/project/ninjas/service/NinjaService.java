package com.raphael.project.ninjas.service;

import com.raphael.project.ninjas.dto.GlobalResponse;
import com.raphael.project.ninjas.dto.NinjaRequest;
import com.raphael.project.ninjas.dto.NinjaResponse;
import com.raphael.project.ninjas.enums.NinjaRanks;
import com.raphael.project.ninjas.enums.NinjaVilas;
import com.raphael.project.ninjas.mapper.NinjaMapper;
import com.raphael.project.ninjas.model.Ninja;
import com.raphael.project.ninjas.repository.NinjaRepository;
import com.raphael.project.ninjas.utils.messages.MessageExceptionResponses;
import com.raphael.project.ninjas.utils.messages.MessageResponses;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NinjaService {
    private final NinjaRepository repository;
    private final NinjaMapper mapper;
    private final MessageResponses messageResponses;
    private final MessageExceptionResponses messageExceptionResponses;

    public GlobalResponse<NinjaResponse> inserir(NinjaRequest request) {
        var ninja = mapper.toEntity(request);
        repository.save(ninja);
        var response = mapper.toDTO(ninja);
        return messageResponses.inserirSucesso(response);
    }

    public GlobalResponse<List<NinjaResponse>> buscarTodos() {
        List<Ninja> ninjas = repository.findAll();
        var response = mapper.toDTOList(ninjas);
        return messageResponses.buscarTodosSucesso(response);
    }

    public GlobalResponse<NinjaResponse> buscarPorId(Long id) {
        Ninja ninja = repository.findById(id)
                .orElseThrow(() -> messageExceptionResponses.notFoundException("ninja.not.found", id));
        var response = mapper.toDTO(ninja);
        return messageResponses.buscarPorIdSucesso(response, id);
    }

    public GlobalResponse<NinjaResponse> deletarPorId(Long id) {
        Ninja ninja = repository.findById(id)
                .orElseThrow(() -> messageExceptionResponses.notFoundException("ninja.not.found", id));
        repository.deleteById(id);
        var response = mapper.toDTO(ninja);
        return messageResponses.deletarPorId(response, id);
    }

    public GlobalResponse<NinjaResponse> atualizarPorId(Long id, NinjaRequest request) {
        Ninja ninja = repository.findById(id)
                .orElseThrow(() -> messageExceptionResponses.notFoundException("ninja.not.found", id));
        ninja.setNome(request.nome());
        ninja.setVila(NinjaVilas.valueOf(request.vila().toUpperCase()));
        ninja.setRank(NinjaRanks.valueOf(request.rank().toUpperCase()));
        ninja.setPoder(request.poder());
        var ninjaAtualizado = mapper.toDTO(ninja);
        return messageResponses.atualizarPorId(ninjaAtualizado, id);
    }
}
