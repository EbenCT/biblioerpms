package com.sw2parcial2.biblioerpms.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreatePrestamoInput {
    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    private LocalDate fechaDevolucion;

    @NotNull
    private Long miembroId;

    private List<CreateDetallePrestamoInput> detalles;
}
