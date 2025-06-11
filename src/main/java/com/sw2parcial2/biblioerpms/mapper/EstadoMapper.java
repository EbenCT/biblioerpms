package com.sw2parcial2.biblioerpms.mapper;

import com.sw2parcial2.biblioerpms.dto.EstadoDTO;
import com.sw2parcial2.biblioerpms.entity.Estado;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EstadoMapper {

    EstadoDTO toDTO(Estado entity);

    List<EstadoDTO> toDTO(List<Estado> entities);
}
