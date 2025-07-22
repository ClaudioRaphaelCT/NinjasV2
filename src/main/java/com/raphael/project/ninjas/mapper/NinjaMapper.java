package com.raphael.project.ninjas.mapper;

import com.raphael.project.ninjas.dto.NinjaRequest;
import com.raphael.project.ninjas.dto.NinjaResponse;
import com.raphael.project.ninjas.model.Ninja;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface NinjaMapper {
    Ninja toEntity(NinjaRequest request);

    NinjaResponse toDTO(Ninja response);

    List<NinjaResponse> toDTOList(List<Ninja> responseList);
}
