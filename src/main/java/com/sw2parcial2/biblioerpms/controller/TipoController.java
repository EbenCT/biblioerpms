package com.sw2parcial2.biblioerpms.controller;

import com.sw2parcial2.biblioerpms.dto.TipoDTO;
import com.sw2parcial2.biblioerpms.mapper.TipoMapper;
import com.sw2parcial2.biblioerpms.repository.TipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TipoController {

    private final TipoRepository tipoRepository;
    private final TipoMapper tipoMapper;

    @QueryMapping
    public List<TipoDTO> tipos() {
        return tipoMapper.toDTO(tipoRepository.findAll());
    }

    @QueryMapping
    public TipoDTO tipo(@Argument Long id) {
        return tipoRepository.findById(id)
                .map(tipoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado: " + id));
    }
}
