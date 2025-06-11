package com.sw2parcial2.biblioerpms.service;

import com.sw2parcial2.biblioerpms.dto.EstadoDTO;
import com.sw2parcial2.biblioerpms.entity.Estado;
import com.sw2parcial2.biblioerpms.mapper.EstadoMapper;
import com.sw2parcial2.biblioerpms.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EstadoService {

    private final EstadoRepository estadoRepository;
    private final EstadoMapper estadoMapper;

    public List<EstadoDTO> findAll() {
        return estadoMapper.toDTO(estadoRepository.findAll());
    }

    public Optional<EstadoDTO> findById(Long id) {
        return estadoRepository.findById(id)
                .map(estadoMapper::toDTO);
    }

    public Optional<EstadoDTO> findByNombre(String nombre) {
        return estadoRepository.findByNombre(nombre)
                .map(estadoMapper::toDTO);
    }

    public EstadoDTO create(EstadoDTO estadoDTO) {
        if (estadoRepository.findByNombre(estadoDTO.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe un estado con el nombre: " + estadoDTO.getNombre());
        }

        Estado estado = Estado.builder()
                .nombre(estadoDTO.getNombre())
                .descripcion(estadoDTO.getDescripcion())
                .build();

        Estado saved = estadoRepository.save(estado);
        return estadoMapper.toDTO(saved);
    }

    public EstadoDTO update(Long id, EstadoDTO estadoDTO) {
        Estado estado = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado: " + id));

        // Verificar que no exista otro estado con el mismo nombre
        Optional<Estado> existingEstado = estadoRepository.findByNombre(estadoDTO.getNombre());
        if (existingEstado.isPresent() && !existingEstado.get().getId().equals(id)) {
            throw new RuntimeException("Ya existe un estado con el nombre: " + estadoDTO.getNombre());
        }

        estado.setNombre(estadoDTO.getNombre());
        estado.setDescripcion(estadoDTO.getDescripcion());

        Estado saved = estadoRepository.save(estado);
        return estadoMapper.toDTO(saved);
    }

    public void delete(Long id) {
        Estado estado = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado: " + id));

        // Verificar que no haya detalles de estado asociados
        if (!estado.getDetallesEstado().isEmpty()) {
            throw new RuntimeException("No se puede eliminar el estado porque tiene registros asociados");
        }

        estadoRepository.deleteById(id);
    }
}
