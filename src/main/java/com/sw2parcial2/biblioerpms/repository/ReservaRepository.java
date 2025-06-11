package com.sw2parcial2.biblioerpms.repository;

import com.sw2parcial2.biblioerpms.entity.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("SELECT r FROM Reserva r LEFT JOIN FETCH r.miembro LEFT JOIN FETCH r.detalles")
    Page<Reserva> findAllWithDetails(Pageable pageable);

    @Query("SELECT r FROM Reserva r WHERE r.miembro.id = :miembroId")
    List<Reserva> findByMiembroId(@Param("miembroId") Long miembroId);

    @Query("SELECT r FROM Reserva r WHERE r.fechaRecojo = :fecha")
    List<Reserva> findReservasParaHoy(@Param("fecha") LocalDate fecha);
}
