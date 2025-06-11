package com.sw2parcial2.biblioerpms.repository;

import com.sw2parcial2.biblioerpms.entity.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long> {
    Optional<Tipo> findByNombre(String nombre);
}