package com.sw2parcial2.biblioerpms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "ejemplar")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ejemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @NotNull
    @Min(0)
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Size(max = 100)
    @Column(name = "editorial", length = 100)
    private String editorial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_id")
    private Tipo tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @OneToMany(mappedBy = "ejemplar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetallePrestamo> detallesPrestamo;

    @OneToMany(mappedBy = "ejemplar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleReserva> detallesReserva;

    @OneToMany(mappedBy = "ejemplar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleEstado> detallesEstado;
}
