package com.raphael.project.services;

import com.raphael.project.ninjas.dto.GlobalResponse;
import com.raphael.project.ninjas.dto.NinjaRequest;
import com.raphael.project.ninjas.dto.NinjaResponse;
import com.raphael.project.ninjas.enums.NinjaRanks;
import com.raphael.project.ninjas.enums.NinjaVilas;
import com.raphael.project.ninjas.exceptions.NinjaNotFoundException;
import com.raphael.project.ninjas.mapper.NinjaMapper;
import com.raphael.project.ninjas.model.Ninja;
import com.raphael.project.ninjas.repository.NinjaRepository;
import com.raphael.project.ninjas.service.NinjaService;
import com.raphael.project.ninjas.utils.messages.MessageExceptionResponses;
import com.raphael.project.ninjas.utils.messages.MessageResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NinjaServicesTests {

    @Mock
    private NinjaRepository repository;
    @Mock
    private NinjaMapper mapper;
    @Mock
    private MessageResponses messageResponses;
    @Mock
    private MessageExceptionResponses messageExceptionResponses;

    @InjectMocks
    private NinjaService service;

    private Ninja ninja;
    private Ninja ninjaSemId;
    private NinjaRequest ninjaRequest;
    private NinjaResponse ninjaResponse;
    private GlobalResponse<NinjaResponse> globalResponse;

    // Adicione os objetos para o teste de "buscar todos" no setUp
    private Ninja ninjaSasuke;
    private NinjaResponse ninjaResponseSasuke;
    private List<Ninja> allNinjas;
    private List<NinjaResponse> allNinjaResponses;
    private GlobalResponse<List<NinjaResponse>> allNinjasGlobalResponse;

    @BeforeEach
    void setUp() {
        ninja = Ninja.builder().id(1L).createdAt(LocalDateTime.now())
                .nome("NARUTO").vila(NinjaVilas.valueOf("FOLHA"))
                .rank(NinjaRanks.valueOf("KAGE")).poder(800.00).build();

        ninjaSemId = Ninja.builder().nome("NARUTO").vila(NinjaVilas.valueOf("FOLHA"))
                .rank(NinjaRanks.valueOf("KAGE")).poder(800.00).build();

        ninjaRequest = new NinjaRequest("NARUTO", "FOLHA", "KAGE", 800.00);
        ninjaResponse = new NinjaResponse(
                1L, LocalDateTime.now(), "NARUTO", "FOLHA",
                "KAGE", 800.00);

        globalResponse = new GlobalResponse<>(
                LocalDateTime.now(),
                "O ninja foi inserido com sucesso.", ninjaResponse);

        // Objetos para o teste de buscar todos
        ninjaSasuke = Ninja.builder().id(2L).createdAt(LocalDateTime.now())
                .nome("SASUKE").vila(NinjaVilas.valueOf("FOLHA"))
                .rank(NinjaRanks.valueOf("CHUNIN")).poder(700.00).build();

        ninjaResponseSasuke = new NinjaResponse(
                2L, LocalDateTime.now(), "SASUKE", "FOLHA",
                "CHUNIN", 700.00);

        allNinjas = List.of(ninja, ninjaSasuke);
        allNinjaResponses = List.of(ninjaResponse, ninjaResponseSasuke);

        allNinjasGlobalResponse = new GlobalResponse<>(
                LocalDateTime.now(),
                "Busca para visualizar todos os ninjas, realizada com sucesso.",
                allNinjaResponses
        );
    }

    @Test
    @DisplayName("Deve inserir um NINJA e retornar OK")
    void inserirNinjaRetornoOK() {
        // 1 - Converter o request para entidade.
        when(mapper.toEntity(ninjaRequest)).thenReturn(ninjaSemId);
        // 2 - Simular o insert
        when(repository.save(any(Ninja.class))).thenReturn(ninja);
        // 3 - Converter o response para DTO
        when(mapper.toDTO(any(Ninja.class))).thenReturn(ninjaResponse);
        // 4 - Criar mensagem de retorno para o usuario
        when(messageResponses.inserirSucesso(any(NinjaResponse.class))).thenReturn(globalResponse);

        GlobalResponse<NinjaResponse> result = service.inserir(ninjaRequest);
        // Verificar retorno do passo 1
        verify(mapper).toEntity(ninjaRequest);
        // Verificar retorno do passo 2
        verify(repository).save(ninjaSemId);
        //Verificar retorno do passo 3
        verify(mapper).toDTO(any(Ninja.class));
        // Verificar retorno do passo 4
        verify(messageResponses).inserirSucesso(any(NinjaResponse.class));

        //VALIDAÇÕES
        // Se o campo da resposta é nulo.
        assertNotNull(result, "O resultado não pode ser nulo.");
        // Se o retorno da mensagem é o mesmo do esperado.
        assertEquals("O ninja foi inserido com sucesso.",
                result.mensagem(),
                "A mensagem de retorno diferente");
        // Se os dados inseridos são o mesmo do esperado.
        assertEquals(ninjaResponse, result.dados(), "O dados estão divergentes.");
    }

    @Test
    @DisplayName("Deve buscar um Ninja por ID e retornar OK")
    void deveBuscarNinjaPorIdRetornarOK() {
        Long ninjaId = 1L;

        GlobalResponse<NinjaResponse> expectedNinja = new GlobalResponse<>(
                LocalDateTime.now(),
                "Busca para visualizar o ninja id: " + ninjaId + ", realizada com sucesso.",
                ninjaResponse
        );

        // 1 - Buscar por id
        when(repository.findById(1L)).thenReturn(Optional.of(ninja));
        // 2 - Passar a entidade para DTO
        when(mapper.toDTO(ninja)).thenReturn(ninjaResponse);
        // 3 - Retornar mensagem para usuario
        when(messageResponses.buscarPorIdSucesso(any(NinjaResponse.class), eq(ninjaId))).thenReturn(expectedNinja);

        GlobalResponse<NinjaResponse> result = service.buscarPorId(ninjaId);

        // VERIFICAÇÕES
        // Verificar se os mocks foram chamados corretamente.
        verify(repository).findById(ninjaId);
        verify(mapper).toDTO(ninja);
        verify(messageResponses).buscarPorIdSucesso(any(NinjaResponse.class), eq(ninjaId));

        //  Validação retorno não nullo.
        assertNotNull(result, "O resultado não pode ser nulo.");
        // Validação de retorno de mensagens iguais.
        assertEquals(expectedNinja.mensagem(), result.mensagem(), "A mensagem da resposta não corresponde.");
        // Validação de retorno de dados iguais.
        assertEquals(result.dados(), expectedNinja.dados(), "Os dados são diferentes.");
    }

    @Test
    @DisplayName("Deve lançar uma NinjaNotFoundException")
    void deveRetornarNinjaNotFoundException() {
        Long idInexistente = 99L;
        String expectedMessage = "Nao existe ninja com o id: " + idInexistente + "na base de dados";

        // 1 - Buscar por ID e retornar vazio.
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());
        // 2 - Mockar o lançamento da exception
        when(messageExceptionResponses.notFoundException(any(String.class), any(Long.class)))
                .thenThrow(new NinjaNotFoundException(expectedMessage));

        NinjaNotFoundException thrown = assertThrows(
                NinjaNotFoundException.class,
                () -> service.buscarPorId(idInexistente)
        );

        // VERIFICAÇÕES
        // Verificar id Inexistente
        verify(repository).findById(idInexistente);
        // Verifica se a mensagem de retorno é igual o que vem no notFoudException
        verify(messageExceptionResponses).notFoundException("ninja.not.found", idInexistente);

        // Validar se a mensagem recebida é igual
        assertEquals(expectedMessage, thrown.getMessage());

    }

    @Test
    @DisplayName("Deve buscar todos e retornar sucesso")
    void deveBuscarTodosNinjasRetornarOK() {

        // Chamar o repositorio para obter a lista de ninjas
        when(repository.findAll()).thenReturn(allNinjas);
        // Chamar o mapper.
        when(mapper.toDTOList(allNinjas)).thenReturn(allNinjaResponses);
        //when(messageResponses.buscarTodosSucesso(any(List.class))).thenReturn(allNinjasGlobalResponse);

        //ACT
        GlobalResponse<List<NinjaResponse>> result = service.buscarTodos();

        verify(repository).findAll();
        verify(mapper).toDTOList(allNinjas);
        //verify(messageResponses).buscarTodosSucesso(any(List.class));
    }
}
