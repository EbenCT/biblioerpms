package com.sw2parcial2.biblioerpms.dto.input;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateMiembroInput {
    @NotBlank
    @Size(max = 20)
    private String ci;

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @Size(max = 200)
    private String direccion;

    private Integer celular;

    @Pattern(regexp = "^[MF]$")
    private String sexo;

    @Min(0)
    @Max(120)
    private Integer edad;

    private Long usuarioId;
}
