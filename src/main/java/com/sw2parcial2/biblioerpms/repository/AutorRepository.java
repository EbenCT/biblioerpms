package com.sw2parcial2.biblioerpms.repository;

import com.sw2parcial2.biblioerpms.entity.Autor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<Autor> findByNombreContainingIgnoreCase(@Param("nombre") String nombre, Pageable pageable);

    @Query("SELECT a FROM Autor a WHERE a.nacionalidad = :nacionalidad")
    Page<Autor> findByNacionalidad(@Param("nacionalidad") String nacionalidad, Pageable pageable);
}