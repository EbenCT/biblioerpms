package com.sw2parcial2.biblioerpms.dto.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateDetallePrestamoInput {
    @NotNull
    @Min(1)
    private Integer cantidad;

    @NotNull
    private Long ejemplarId;
}
