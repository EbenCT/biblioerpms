package com.sw2parcial2.biblioerpms.controller;

import com.sw2parcial2.biblioerpms.dto.AutorDTO;
import com.sw2parcial2.biblioerpms.service.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @QueryMapping
    public Page<AutorDTO> autores(@Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        return autorService.findAll(pageable);
    }

    @QueryMapping
    public AutorDTO autor(@Argument Long id) {
        return autorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado: " + id));
    }

    @QueryMapping
    public Page<AutorDTO> buscarAutores(@Argument String nombre, @Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        return autorService.searchByNombre(nombre, pageable);
    }

    @QueryMapping
    public Page<AutorDTO> autoresPorNacionalidad(@Argument String nacionalidad, @Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        return autorService.findByNacionalidad(nacionalidad, pageable);
    }

    @QueryMapping
    public List<AutorDTO> todosLosAutores() {
        return autorService.findAll();
    }
}
