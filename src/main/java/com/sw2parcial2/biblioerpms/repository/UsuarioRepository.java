package com.sw2parcial2.biblioerpms.repository;

import com.sw2parcial2.biblioerpms.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.rol")
    Page<Usuario> findAllWithRol(Pageable pageable);

    boolean existsByEmail(String email);
}
