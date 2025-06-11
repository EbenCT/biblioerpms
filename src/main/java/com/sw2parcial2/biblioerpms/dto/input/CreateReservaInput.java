package com.sw2parcial2.biblioerpms.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateReservaInput {
    @NotNull
    private LocalDate fechaRegistro;

    @NotNull
    private LocalDate fechaRecojo;

    @NotNull
    private Long miembroId;

    private List<CreateDetalleReservaInput> detalles;
}
