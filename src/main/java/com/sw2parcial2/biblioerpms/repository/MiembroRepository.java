package com.sw2parcial2.biblioerpms.repository;

import com.sw2parcial2.biblioerpms.entity.Miembro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Long> {
    Optional<Miembro> findByCi(String ci);

    @Query("SELECT m FROM Miembro m WHERE LOWER(m.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Miembro> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    boolean existsByCi(String ci);
}
