package com.sw2parcial2.biblioerpms.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private Long id;
    private LocalDate fechaRegistro;
    private LocalDate fechaRecojo;
    private MiembroDTO miembro;
    private List<DetalleReservaDTO> detalles;
}
