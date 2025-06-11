package com.sw2parcial2.biblioerpms.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EjemplarDTO {
    private Long id;
    private String nombre;
    private Integer stock;
    private String editorial;
    private TipoDTO tipo;
    private AutorDTO autor;
}
