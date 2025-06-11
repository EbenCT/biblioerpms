package com.sw2parcial2.biblioerpms.service;

import com.sw2parcial2.biblioerpms.dto.TipoDTO;
import com.sw2parcial2.biblioerpms.entity.Tipo;
import com.sw2parcial2.biblioerpms.mapper.TipoMapper;
import com.sw2parcial2.biblioerpms.repository.TipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TipoService {

    private final TipoRepository tipoRepository;
    private final TipoMapper tipoMapper;

    public List<TipoDTO> findAll() {
        return tipoMapper.toDTO(tipoRepository.findAll());
    }

    public Optional<TipoDTO> findById(Long id) {
        return tipoRepository.findById(id)
                .map(tipoMapper::toDTO);
    }

    public Optional<TipoDTO> findByNombre(String nombre) {
        return tipoRepository.findByNombre(nombre)
                .map(tipoMapper::toDTO);
    }

    public TipoDTO create(TipoDTO tipoDTO) {
        if (tipoRepository.findByNombre(tipoDTO.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe un tipo con el nombre: " + tipoDTO.getNombre());
        }

        Tipo tipo = Tipo.builder()
                .nombre(tipoDTO.getNombre())
                .descripcion(tipoDTO.getDescripcion())
                .build();

        Tipo saved = tipoRepository.save(tipo);
        return tipoMapper.toDTO(saved);
    }

    public TipoDTO update(Long id, TipoDTO tipoDTO) {
        Tipo tipo = tipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado: " + id));

        // Verificar que no exista otro tipo con el mismo nombre
        Optional<Tipo> existingTipo = tipoRepository.findByNombre(tipoDTO.getNombre());
        if (existingTipo.isPresent() && !existingTipo.get().getId().equals(id)) {
            throw new RuntimeException("Ya existe un tipo con el nombre: " + tipoDTO.getNombre());
        }

        tipo.setNombre(tipoDTO.getNombre());
        tipo.setDescripcion(tipoDTO.getDescripcion());

        Tipo saved = tipoRepository.save(tipo);
        return tipoMapper.toDTO(saved);
    }

    public void delete(Long id) {
        Tipo tipo = tipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado: " + id));

        // Verificar que no haya ejemplares asociados
        if (!tipo.getEjemplares().isEmpty()) {
            throw new RuntimeException("No se puede eliminar el tipo porque tiene ejemplares asociados");
        }

        tipoRepository.deleteById(id);
    }
}
