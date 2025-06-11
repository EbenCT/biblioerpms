package com.sw2parcial2.biblioerpms.controller;

import com.sw2parcial2.biblioerpms.dto.RolDTO;
import com.sw2parcial2.biblioerpms.mapper.RolMapper;
import com.sw2parcial2.biblioerpms.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RolController {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    @QueryMapping
    public List<RolDTO> roles() {
        return rolMapper.toDTO(rolRepository.findAll());
    }

    @QueryMapping
    public RolDTO rol(@Argument Long id) {
        return rolRepository.findById(id)
                .map(rolMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + id));
    }
}
