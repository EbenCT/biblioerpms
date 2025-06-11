package com.sw2parcial2.biblioerpms.mapper;

import com.sw2parcial2.biblioerpms.dto.DetalleReservaDTO;
import com.sw2parcial2.biblioerpms.entity.DetalleReserva;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EjemplarMapper.class})
public interface DetalleReservaMapper {

    DetalleReservaDTO toDTO(DetalleReserva entity);

    List<DetalleReservaDTO> toDTO(List<DetalleReserva> entities);
}
