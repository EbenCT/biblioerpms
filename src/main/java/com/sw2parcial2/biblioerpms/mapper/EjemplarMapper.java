package com.sw2parcial2.biblioerpms.mapper;

import com.sw2parcial2.biblioerpms.dto.EjemplarDTO;
import com.sw2parcial2.biblioerpms.dto.input.CreateEjemplarInput;
import com.sw2parcial2.biblioerpms.entity.Ejemplar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TipoMapper.class, AutorMapper.class})
public interface EjemplarMapper {

    EjemplarDTO toDTO(Ejemplar entity);

    List<EjemplarDTO> toDTO(List<Ejemplar> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipo", ignore = true)
    @Mapping(target = "autor", ignore = true)
    @Mapping(target = "detallesPrestamo", ignore = true)
    @Mapping(target = "detallesReserva", ignore = true)
    @Mapping(target = "detallesEstado", ignore = true)
    Ejemplar toEntity(CreateEjemplarInput input);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipo", ignore = true)
    @Mapping(target = "autor", ignore = true)
    @Mapping(target = "detallesPrestamo", ignore = true)
    @Mapping(target = "detallesReserva", ignore = true)
    @Mapping(target = "detallesEstado", ignore = true)
    void updateEntity(CreateEjemplarInput input, @MappingTarget Ejemplar entity);
}
