package com.sw2parcial2.biblioerpms.controller;

import com.sw2parcial2.biblioerpms.dto.EjemplarDTO;
import com.sw2parcial2.biblioerpms.dto.input.CreateEjemplarInput;
import com.sw2parcial2.biblioerpms.service.EjemplarService;
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
public class EjemplarController {

    private final EjemplarService ejemplarService;

    @QueryMapping
    public Page<EjemplarDTO> ejemplares(@Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ejemplarService.findAll(pageable);
    }

    @QueryMapping
    public EjemplarDTO ejemplar(@Argument Long id) {
        return ejemplarService.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado: " + id));
    }

    @QueryMapping
    public Page<EjemplarDTO> buscarEjemplares(@Argument String nombre, @Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ejemplarService.searchByNombre(nombre, pageable);
    }

    @QueryMapping
    public List<EjemplarDTO> ejemplaresPorTipo(@Argument Long tipoId) {
        return ejemplarService.findByTipoId(tipoId);
    }

    @QueryMapping
    public List<EjemplarDTO> ejemplaresPorAutor(@Argument Long autorId) {
        return ejemplarService.findByAutorId(autorId);
    }

    @QueryMapping
    public List<EjemplarDTO> ejemplaresDisponibles() {
        return ejemplarService.findEjemplaresDisponibles();
    }

    @MutationMapping
    public EjemplarDTO crearEjemplar(@Argument CreateEjemplarInput input) {
        return ejemplarService.create(input);
    }

    @MutationMapping
    public EjemplarDTO actualizarEjemplar(@Argument Long id, @Argument CreateEjemplarInput input) {
        return ejemplarService.update(id, input);
    }

    @MutationMapping
    public Boolean eliminarEjemplar(@Argument Long id) {
        ejemplarService.delete(id);
        return true;
    }
}
