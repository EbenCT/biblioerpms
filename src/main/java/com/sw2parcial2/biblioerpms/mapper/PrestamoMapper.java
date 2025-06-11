package com.sw2parcial2.biblioerpms.mapper;

import com.sw2parcial2.biblioerpms.dto.PrestamoDTO;
import com.sw2parcial2.biblioerpms.dto.input.CreatePrestamoInput;
import com.sw2parcial2.biblioerpms.entity.Prestamo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MiembroMapper.class, DetallePrestamoMapper.class})
public interface PrestamoMapper {

    PrestamoDTO toDTO(Prestamo entity);

    List<PrestamoDTO> toDTO(List<Prestamo> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "miembro", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    Prestamo toEntity(CreatePrestamoInput input);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "miembro", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    void updateEntity(CreatePrestamoInput input, @MappingTarget Prestamo entity);
}
