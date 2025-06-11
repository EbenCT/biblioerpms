package com.sw2parcial2.biblioerpms.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiembroDTO {
    private Long id;
    private String ci;
    private String nombre;
    private String direccion;
    private Integer celular;
    private String sexo;
    private Integer edad;
    private UsuarioDTO usuario;
}
