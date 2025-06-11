package com.sw2parcial2.biblioerpms.controller;

import com.sw2parcial2.biblioerpms.dto.UsuarioDTO;
import com.sw2parcial2.biblioerpms.dto.input.CreateUsuarioInput;
import com.sw2parcial2.biblioerpms.service.UsuarioService;
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
public class UsuarioController {

    private final UsuarioService usuarioService;

    @QueryMapping
    public Page<UsuarioDTO> usuarios(@Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        return usuarioService.findAll(pageable);
    }

    @QueryMapping
    public UsuarioDTO usuario(@Argument Long id) {
        return usuarioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
    }

    @QueryMapping
    public UsuarioDTO usuarioByEmail(@Argument String email) {
        return usuarioService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));
    }

    @MutationMapping
    public UsuarioDTO crearUsuario(@Argument CreateUsuarioInput input) {
        return usuarioService.create(input);
    }

    @MutationMapping
    public UsuarioDTO actualizarUsuario(@Argument Long id, @Argument CreateUsuarioInput input) {
        return usuarioService.update(id, input);
    }

    @MutationMapping
    public Boolean eliminarUsuario(@Argument Long id) {
        usuarioService.delete(id);
        return true;
    }
}
