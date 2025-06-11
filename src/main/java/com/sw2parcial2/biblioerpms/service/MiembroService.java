package com.sw2parcial2.biblioerpms.service;

import com.sw2parcial2.biblioerpms.dto.MiembroDTO;
import com.sw2parcial2.biblioerpms.dto.input.CreateMiembroInput;
import com.sw2parcial2.biblioerpms.entity.Miembro;
import com.sw2parcial2.biblioerpms.entity.Usuario;
import com.sw2parcial2.biblioerpms.mapper.MiembroMapper;
import com.sw2parcial2.biblioerpms.repository.MiembroRepository;
import com.sw2parcial2.biblioerpms.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MiembroService {

    private final MiembroRepository miembroRepository;
    private final UsuarioRepository usuarioRepository;
    private final MiembroMapper miembroMapper;

    public Page<MiembroDTO> findAll(Pageable pageable) {
        return miembroRepository.findAll(pageable)
                .map(miembroMapper::toDTO);
    }

    public Optional<MiembroDTO> findById(Long id) {
        return miembroRepository.findById(id)
                .map(miembroMapper::toDTO);
    }

    public Optional<MiembroDTO> findByCi(String ci) {
        return miembroRepository.findByCi(ci)
                .map(miembroMapper::toDTO);
    }

    public Page<MiembroDTO> searchByNombre(String nombre, Pageable pageable) {
        return miembroRepository.findByNombreContainingIgnoreCase(nombre, pageable)
                .map(miembroMapper::toDTO);
    }

    public MiembroDTO create(CreateMiembroInput input) {
        if (miembroRepository.existsByCi(input.getCi())) {
            throw new RuntimeException("CI ya existe: " + input.getCi());
        }

        Miembro miembro = miembroMapper.toEntity(input);

        if (input.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(input.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + input.getUsuarioId()));
            miembro.setUsuario(usuario);
        }

        Miembro saved = miembroRepository.save(miembro);
        return miembroMapper.toDTO(saved);
    }

    public MiembroDTO update(Long id, CreateMiembroInput input) {
        Miembro miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado: " + id));

        // Verificar si el CI ya existe en otro miembro (solo si el CI ha cambiado)
        if (!miembro.getCi().equals(input.getCi()) && miembroRepository.existsByCi(input.getCi())) {
            throw new RuntimeException("CI ya existe: " + input.getCi());
        }

        // Actualizar los campos del miembro usando el mapper
        miembroMapper.updateEntity(input, miembro);

        // Actualizar la relación con usuario si se proporciona
        if (input.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(input.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + input.getUsuarioId()));
            miembro.setUsuario(usuario);
        } else {
            miembro.setUsuario(null);
        }

        Miembro saved = miembroRepository.save(miembro);
        return miembroMapper.toDTO(saved);
    }

    public void delete(Long id) {
        if (!miembroRepository.existsById(id)) {
            throw new RuntimeException("Miembro no encontrado: " + id);
        }
        miembroRepository.deleteById(id);
    }
}
