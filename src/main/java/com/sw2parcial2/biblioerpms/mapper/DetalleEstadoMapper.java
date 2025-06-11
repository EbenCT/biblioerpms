package com.sw2parcial2.biblioerpms.mapper;

import com.sw2parcial2.biblioerpms.dto.DetalleEstadoDTO;
import com.sw2parcial2.biblioerpms.entity.DetalleEstado;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EstadoMapper.class, EjemplarMapper.class})
public interface DetalleEstadoMapper {

    DetalleEstadoDTO toDTO(DetalleEstado entity);

    List<DetalleEstadoDTO> toDTO(List<DetalleEstado> entities);
}
