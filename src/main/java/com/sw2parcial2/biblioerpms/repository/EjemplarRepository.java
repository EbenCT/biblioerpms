package com.sw2parcial2.biblioerpms.repository;

import com.sw2parcial2.biblioerpms.entity.Ejemplar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {
    @Query("SELECT e FROM Ejemplar e LEFT JOIN FETCH e.tipo LEFT JOIN FETCH e.autor")
    Page<Ejemplar> findAllWithDetails(Pageable pageable);

    @Query("SELECT e FROM Ejemplar e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Ejemplar> findByNombreContainingIgnoreCase(@Param("nombre") String nombre, Pageable pageable);

    @Query("SELECT e FROM Ejemplar e WHERE e.tipo.id = :tipoId")
    List<Ejemplar> findByTipoId(@Param("tipoId") Long tipoId);

    @Query("SELECT e FROM Ejemplar e WHERE e.autor.id = :autorId")
    List<Ejemplar> findByAutorId(@Param("autorId") Long autorId);

    @Query("SELECT e FROM Ejemplar e WHERE e.stock > 0")
    List<Ejemplar> findEjemplaresDisponibles();
}

