package com.sw2parcial2.biblioerpms.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleEstadoDTO {
    private Long id;
    private LocalDate fecha;
    private EstadoDTO estado;
    private EjemplarDTO ejemplar;
}
