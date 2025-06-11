package com.sw2parcial2.biblioerpms.controller;

import com.sw2parcial2.biblioerpms.dto.PrestamoDTO;
import com.sw2parcial2.biblioerpms.dto.input.CreatePrestamoInput;
import com.sw2parcial2.biblioerpms.service.PrestamoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService prestamoService;

    @QueryMapping
    public Page<PrestamoDTO> prestamos(@Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        return prestamoService.findAll(pageable);
    }

    @QueryMapping
    public PrestamoDTO prestamo(@Argument Long id) {
        return prestamoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado: " + id));
    }

    @QueryMapping
    public List<PrestamoDTO> prestamosPorMiembro(@Argument Long miembroId) {
        return prestamoService.findByMiembroId(miembroId);
    }

    @QueryMapping
    public List<PrestamoDTO> prestamosVencidos() {
        return prestamoService.findPrestamosVencidos();
    }

    @MutationMapping
    public PrestamoDTO crearPrestamo(@Argument CreatePrestamoInput input) {
        return prestamoService.create(input);
    }

    @MutationMapping
    public Boolean eliminarPrestamo(@Argument Long id) {
        prestamoService.delete(id);
        return true;
    }
}
