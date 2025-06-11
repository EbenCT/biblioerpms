package com.sw2parcial2.biblioerpms.mapper;

import com.sw2parcial2.biblioerpms.dto.TipoDTO;
import com.sw2parcial2.biblioerpms.entity.Tipo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipoMapper {

    TipoDTO toDTO(Tipo entity);

    List<TipoDTO> toDTO(List<Tipo> entities);
}