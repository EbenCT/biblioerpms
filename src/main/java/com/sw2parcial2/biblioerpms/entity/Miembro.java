package com.sw2parcial2.biblioerpms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "miembro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Miembro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "ci", nullable = false, length = 20, unique = true)
    private String ci;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Size(max = 200)
    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "celular")
    private Integer celular;

    @Size(max = 1)
    @Pattern(regexp = "^[MF]$")
    @Column(name = "sexo", length = 1)
    private String sexo;

    @Min(0)
    @Max(120)
    @Column(name = "edad")
    private Integer edad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "miembro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prestamo> prestamos;

    @OneToMany(mappedBy = "miembro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas;
}
