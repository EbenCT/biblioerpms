package com.sw2parcial2.biblioerpms.mapper;

import com.sw2parcial2.biblioerpms.dto.ReservaDTO;
import com.sw2parcial2.biblioerpms.entity.Reserva;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MiembroMapper.class, DetalleReservaMapper.class})
public interface ReservaMapper {

    ReservaDTO toDTO(Reserva entity);

    List<ReservaDTO> toDTO(List<Reserva> entities);
}
