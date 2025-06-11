package com.sw2parcial2.biblioerpms.controller;

import com.sw2parcial2.biblioerpms.dto.MiembroDTO;
import com.sw2parcial2.biblioerpms.dto.input.CreateMiembroInput;
import com.sw2parcial2.biblioerpms.service.MiembroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MiembroController {

    private final MiembroService miembroService;

    @QueryMapping
    public Page<MiembroDTO> miembros(@Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        return miembroService.findAll(pageable);
    }

    @QueryMapping
    public MiembroDTO miembro(@Argument Long id) {
        return miembroService.findById(id)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado: " + id));
    }

    @QueryMapping
    public MiembroDTO miembroByCi(@Argument String ci) {
        return miembroService.findByCi(ci)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado: " + ci));
    }

    @QueryMapping
    public Page<MiembroDTO> buscarMiembros(@Argument String nombre, @Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        return miembroService.searchByNombre(nombre, pageable);
    }

    @MutationMapping
    public MiembroDTO crearMiembro(@Argument CreateMiembroInput input) {
        return miembroService.create(input);
    }

    @MutationMapping
    public MiembroDTO actualizarMiembro(@Argument Long id, @Argument CreateMiembroInput input) {
        return miembroService.update(id, input);
    }

    @MutationMapping
    public Boolean eliminarMiembro(@Argument Long id) {
        miembroService.delete(id);
        return true;
    }
}
