package com.sw2parcial2.biblioerpms.service;

import com.sw2parcial2.biblioerpms.dto.UsuarioDTO;
import com.sw2parcial2.biblioerpms.dto.input.CreateUsuarioInput;
import com.sw2parcial2.biblioerpms.entity.Rol;
import com.sw2parcial2.biblioerpms.entity.Usuario;
import com.sw2parcial2.biblioerpms.mapper.UsuarioMapper;
import com.sw2parcial2.biblioerpms.repository.RolRepository;
import com.sw2parcial2.biblioerpms.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public Page<UsuarioDTO> findAll(Pageable pageable) {
        return usuarioRepository.findAllWithRol(pageable)
                .map(usuarioMapper::toDTO);
    }

    public Optional<UsuarioDTO> findById(Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO);
    }

    public Optional<UsuarioDTO> findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuarioMapper::toDTO);
    }

    public UsuarioDTO create(CreateUsuarioInput input) {
        if (usuarioRepository.existsByEmail(input.getEmail())) {
            throw new RuntimeException("Email ya existe: " + input.getEmail());
        }

        Usuario usuario = usuarioMapper.toEntity(input);
        usuario.setPassword(passwordEncoder.encode(input.getPassword()));

        if (input.getRolId() != null) {
            Rol rol = rolRepository.findById(input.getRolId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + input.getRolId()));
            usuario.setRol(rol);
        }

        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(saved);
    }

    public UsuarioDTO update(Long id, CreateUsuarioInput input) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));

        if (!usuario.getEmail().equals(input.getEmail()) &&
                usuarioRepository.existsByEmail(input.getEmail())) {
            throw new RuntimeException("Email ya existe: " + input.getEmail());
        }

        usuarioMapper.updateEntity(input, usuario);

        if (input.getPassword() != null && !input.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(input.getPassword()));
        }

        if (input.getRolId() != null) {
            Rol rol = rolRepository.findById(input.getRolId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + input.getRolId()));
            usuario.setRol(rol);
        }

        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(saved);
    }

    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
