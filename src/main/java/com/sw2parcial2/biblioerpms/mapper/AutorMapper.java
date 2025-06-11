package com.sw2parcial2.biblioerpms.mapper;

import com.sw2parcial2.biblioerpms.dto.AutorDTO;
import com.sw2parcial2.biblioerpms.entity.Autor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    AutorDTO toDTO(Autor entity);

    List<AutorDTO> toDTO(List<Autor> entities);
}
