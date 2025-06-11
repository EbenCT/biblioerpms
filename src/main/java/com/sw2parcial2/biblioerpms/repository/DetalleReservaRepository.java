package com.sw2parcial2.biblioerpms.repository;

import com.sw2parcial2.biblioerpms.entity.DetalleReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleReservaRepository extends JpaRepository<DetalleReserva, Long> {
    @Query("SELECT dr FROM DetalleReserva dr WHERE dr.reserva.id = :reservaId")
    List<DetalleReserva> findByReservaId(@Param("reservaId") Long reservaId);

    @Query("SELECT dr FROM DetalleReserva dr WHERE dr.ejemplar.id = :ejemplarId")
    List<DetalleReserva> findByEjemplarId(@Param("ejemplarId") Long ejemplarId);
}