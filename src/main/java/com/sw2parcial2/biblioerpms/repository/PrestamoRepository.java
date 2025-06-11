package com.sw2parcial2.biblioerpms.repository;

import com.sw2parcial2.biblioerpms.entity.Prestamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    @Query("SELECT p FROM Prestamo p LEFT JOIN FETCH p.miembro LEFT JOIN FETCH p.detalles")
    Page<Prestamo> findAllWithDetails(Pageable pageable);

    @Query("SELECT p FROM Prestamo p WHERE p.miembro.id = :miembroId")
    List<Prestamo> findByMiembroId(@Param("miembroId") Long miembroId);

    @Query("SELECT p FROM Prestamo p WHERE p.fechaDevolucion < :fecha")
    List<Prestamo> findPrestamosVencidos(@Param("fecha") LocalDate fecha);
}
