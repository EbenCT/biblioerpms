package com.sw2parcial2.biblioerpms.mapper;

import com.sw2parcial2.biblioerpms.dto.DetallePrestamoDTO;
import com.sw2parcial2.biblioerpms.dto.input.CreateDetallePrestamoInput;
import com.sw2parcial2.biblioerpms.entity.DetallePrestamo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EjemplarMapper.class})
public interface DetallePrestamoMapper {

    DetallePrestamoDTO toDTO(DetallePrestamo entity);

    List<DetallePrestamoDTO> toDTO(List<DetallePrestamo> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "prestamo", ignore = true)
    @Mapping(target = "ejemplar", ignore = true)
    DetallePrestamo toEntity(CreateDetallePrestamoInput input);
}