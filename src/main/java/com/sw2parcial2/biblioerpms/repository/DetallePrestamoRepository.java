package com.sw2parcial2.biblioerpms.repository;

import com.sw2parcial2.biblioerpms.entity.DetallePrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePrestamoRepository extends JpaRepository<DetallePrestamo, Long> {
    @Query("SELECT dp FROM DetallePrestamo dp WHERE dp.prestamo.id = :prestamoId")
    List<DetallePrestamo> findByPrestamoId(@Param("prestamoId") Long prestamoId);

    @Query("SELECT dp FROM DetallePrestamo dp WHERE dp.ejemplar.id = :ejemplarId")
    List<DetallePrestamo> findByEjemplarId(@Param("ejemplarId") Long ejemplarId);
}
