package com.sw2parcial2.biblioerpms.service;

import com.sw2parcial2.biblioerpms.dto.EjemplarDTO;
import com.sw2parcial2.biblioerpms.dto.input.CreateEjemplarInput;
import com.sw2parcial2.biblioerpms.entity.Autor;
import com.sw2parcial2.biblioerpms.entity.Ejemplar;
import com.sw2parcial2.biblioerpms.entity.Tipo;
import com.sw2parcial2.biblioerpms.mapper.EjemplarMapper;
import com.sw2parcial2.biblioerpms.repository.AutorRepository;
import com.sw2parcial2.biblioerpms.repository.EjemplarRepository;
import com.sw2parcial2.biblioerpms.repository.TipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EjemplarService {

    private final EjemplarRepository ejemplarRepository;
    private final TipoRepository tipoRepository;
    private final AutorRepository autorRepository;
    private final EjemplarMapper ejemplarMapper;

    public Page<EjemplarDTO> findAll(Pageable pageable) {
        return ejemplarRepository.findAllWithDetails(pageable)
                .map(ejemplarMapper::toDTO);
    }

    public Optional<EjemplarDTO> findById(Long id) {
        return ejemplarRepository.findById(id)
                .map(ejemplarMapper::toDTO);
    }

    public Page<EjemplarDTO> searchByNombre(String nombre, Pageable pageable) {
        return ejemplarRepository.findByNombreContainingIgnoreCase(nombre, pageable)
                .map(ejemplarMapper::toDTO);
    }

    public List<EjemplarDTO> findByTipoId(Long tipoId) {
        return ejemplarMapper.toDTO(ejemplarRepository.findByTipoId(tipoId));
    }

    public List<EjemplarDTO> findByAutorId(Long autorId) {
        return ejemplarMapper.toDTO(ejemplarRepository.findByAutorId(autorId));
    }

    public List<EjemplarDTO> findEjemplaresDisponibles() {
        return ejemplarMapper.toDTO(ejemplarRepository.findEjemplaresDisponibles());
    }

    public EjemplarDTO create(CreateEjemplarInput input) {
        Ejemplar ejemplar = ejemplarMapper.toEntity(input);

        if (input.getTipoId() != null) {
            Tipo tipo = tipoRepository.findById(input.getTipoId())
                    .orElseThrow(() -> new RuntimeException("Tipo no encontrado: " + input.getTipoId()));
            ejemplar.setTipo(tipo);
        }

        if (input.getAutorId() != null) {
            Autor autor = autorRepository.findById(input.getAutorId())
                    .orElseThrow(() -> new RuntimeException("Autor no encontrado: " + input.getAutorId()));
            ejemplar.setAutor(autor);
        }

        Ejemplar saved = ejemplarRepository.save(ejemplar);
        return ejemplarMapper.toDTO(saved);
    }

    public EjemplarDTO update(Long id, CreateEjemplarInput input) {
        Ejemplar ejemplar = ejemplarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado: " + id));

        ejemplarMapper.updateEntity(input, ejemplar);

        if (input.getTipoId() != null) {
            Tipo tipo = tipoRepository.findById(input.getTipoId())
                    .orElseThrow(() -> new RuntimeException("Tipo no encontrado: " + input.getTipoId()));
            ejemplar.setTipo(tipo);
        }

        if (input.getAutorId() != null) {
            Autor autor = autorRepository.findById(input.getAutorId())
                    .orElseThrow(() -> new RuntimeException("Autor no encontrado: " + input.getAutorId()));
            ejemplar.setAutor(autor);
        }

        Ejemplar saved = ejemplarRepository.save(ejemplar);
        return ejemplarMapper.toDTO(saved);
    }

    public void delete(Long id) {
        if (!ejemplarRepository.existsById(id)) {
            throw new RuntimeException("Ejemplar no encontrado: " + id);
        }
        ejemplarRepository.deleteById(id);
    }
}
