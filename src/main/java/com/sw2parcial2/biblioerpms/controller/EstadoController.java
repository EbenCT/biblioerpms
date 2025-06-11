package com.sw2parcial2.biblioerpms.controller;

import com.sw2parcial2.biblioerpms.dto.EstadoDTO;
import com.sw2parcial2.biblioerpms.mapper.EstadoMapper;
import com.sw2parcial2.biblioerpms.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoRepository estadoRepository;
    private final EstadoMapper estadoMapper;

    @QueryMapping
    public List<EstadoDTO> estados() {
        return estadoMapper.toDTO(estadoRepository.findAll());
    }

    @QueryMapping
    public EstadoDTO estado(@Argument Long id) {
        return estadoRepository.findById(id)
                .map(estadoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado: " + id));
    }
}
