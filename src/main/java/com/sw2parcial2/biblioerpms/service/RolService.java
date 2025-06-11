package com.sw2parcial2.biblioerpms.service;

import com.sw2parcial2.biblioerpms.dto.RolDTO;
import com.sw2parcial2.biblioerpms.entity.Rol;
import com.sw2parcial2.biblioerpms.mapper.RolMapper;
import com.sw2parcial2.biblioerpms.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    public List<RolDTO> findAll() {
        return rolMapper.toDTO(rolRepository.findAll());
    }

    public Optional<RolDTO> findById(Long id) {
        return rolRepository.findById(id)
                .map(rolMapper::toDTO);
    }

    public Optional<RolDTO> findByName(String name) {
        return rolRepository.findByName(name)
                .map(rolMapper::toDTO);
    }

    public RolDTO create(RolDTO rolDTO) {
        if (rolRepository.findByName(rolDTO.getName()).isPresent()) {
            throw new RuntimeException("Ya existe un rol con el nombre: " + rolDTO.getName());
        }

        Rol rol = Rol.builder()
                .name(rolDTO.getName())
                .build();

        Rol saved = rolRepository.save(rol);
        return rolMapper.toDTO(saved);
    }

    public RolDTO update(Long id, RolDTO rolDTO) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + id));

        // Verificar que no exista otro rol con el mismo nombre
        Optional<Rol> existingRol = rolRepository.findByName(rolDTO.getName());
        if (existingRol.isPresent() && !existingRol.get().getId().equals(id)) {
            throw new RuntimeException("Ya existe un rol con el nombre: " + rolDTO.getName());
        }

        rol.setName(rolDTO.getName());

        Rol saved = rolRepository.save(rol);
        return rolMapper.toDTO(saved);
    }

    public void delete(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + id));

        // Verificar que no haya usuarios asociados
        if (!rol.getUsuarios().isEmpty()) {
            throw new RuntimeException("No se puede eliminar el rol porque tiene usuarios asociados");
        }

        rolRepository.deleteById(id);
    }
}
