package com.sw2parcial2.biblioerpms.repository;

import com.sw2parcial2.biblioerpms.entity.DetalleEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleEstadoRepository extends JpaRepository<DetalleEstado, Long> {
    @Query("SELECT de FROM DetalleEstado de WHERE de.ejemplar.id = :ejemplarId ORDER BY de.fecha DESC")
    List<DetalleEstado> findByEjemplarIdOrderByFechaDesc(@Param("ejemplarId") Long ejemplarId);

    @Query("SELECT de FROM DetalleEstado de WHERE de.estado.id = :estadoId")
    List<DetalleEstado> findByEstadoId(@Param("estadoId") Long estadoId);
}
