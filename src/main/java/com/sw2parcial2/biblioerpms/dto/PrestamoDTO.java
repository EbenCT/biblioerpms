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
public class PrestamoDTO {
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaDevolucion;
    private MiembroDTO miembro;
    private List<DetallePrestamoDTO> detalles;
}
