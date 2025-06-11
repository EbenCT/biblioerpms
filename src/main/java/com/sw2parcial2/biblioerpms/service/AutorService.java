package com.sw2parcial2.biblioerpms.service;

import com.sw2parcial2.biblioerpms.dto.AutorDTO;
import com.sw2parcial2.biblioerpms.entity.Autor;
import com.sw2parcial2.biblioerpms.mapper.AutorMapper;
import com.sw2parcial2.biblioerpms.repository.AutorRepository;
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
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    public Page<AutorDTO> findAll(Pageable pageable) {
        return autorRepository.findAll(pageable)
                .map(autorMapper::toDTO);
    }

    public Optional<AutorDTO> findById(Long id) {
        return autorRepository.findById(id)
                .map(autorMapper::toDTO);
    }

    public Page<AutorDTO> searchByNombre(String nombre, Pageable pageable) {
        return autorRepository.findByNombreContainingIgnoreCase(nombre, pageable)
                .map(autorMapper::toDTO);
    }

    public Page<AutorDTO> findByNacionalidad(String nacionalidad, Pageable pageable) {
        return autorRepository.findByNacionalidad(nacionalidad, pageable)
                .map(autorMapper::toDTO);
    }

    public List<AutorDTO> findAll() {
        return autorMapper.toDTO(autorRepository.findAll());
    }

    public AutorDTO create(AutorDTO autorDTO) {
        Autor autor = Autor.builder()
                .nombre(autorDTO.getNombre())
                .nacionalidad(autorDTO.getNacionalidad())
                .build();

        Autor saved = autorRepository.save(autor);
        return autorMapper.toDTO(saved);
    }

    public AutorDTO update(Long id, AutorDTO autorDTO) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado: " + id));

        autor.setNombre(autorDTO.getNombre());
        autor.setNacionalidad(autorDTO.getNacionalidad());

        Autor saved = autorRepository.save(autor);
        return autorMapper.toDTO(saved);
    }

    public void delete(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new RuntimeException("Autor no encontrado: " + id);
        }
        autorRepository.deleteById(id);
    }
}
