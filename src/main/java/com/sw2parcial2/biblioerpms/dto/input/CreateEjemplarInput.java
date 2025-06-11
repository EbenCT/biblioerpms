package com.sw2parcial2.biblioerpms.dto.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateEjemplarInput {
    @NotBlank
    @Size(max = 200)
    private String nombre;

    @NotNull
    @Min(0)
    private Integer stock;

    @Size(max = 100)
    private String editorial;

    private Long tipoId;
    private Long autorId;
}
