package com.sw2parcial2.biblioerpms.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleReservaDTO {
    private Long id;
    private Integer cantidad;
    private EjemplarDTO ejemplar;
}
