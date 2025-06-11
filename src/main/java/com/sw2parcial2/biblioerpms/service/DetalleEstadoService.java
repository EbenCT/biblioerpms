package com.sw2parcial2.biblioerpms.service;

import com.sw2parcial2.biblioerpms.dto.DetalleEstadoDTO;
import com.sw2parcial2.biblioerpms.entity.DetalleEstado;
import com.sw2parcial2.biblioerpms.entity.Ejemplar;
import com.sw2parcial2.biblioerpms.entity.Estado;
import com.sw2parcial2.biblioerpms.mapper.DetalleEstadoMapper;
import com.sw2parcial2.biblioerpms.repository.DetalleEstadoRepository;
import com.sw2parcial2.biblioerpms.repository.EjemplarRepository;
import com.sw2parcial2.biblioerpms.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DetalleEstadoService {

    private final DetalleEstadoRepository detalleEstadoRepository;
    private final EjemplarRepository ejemplarRepository;
    private final EstadoRepository estadoRepository;
    private final DetalleEstadoMapper detalleEstadoMapper;

    public List<DetalleEstadoDTO> findAll() {
        return detalleEstadoMapper.toDTO(detalleEstadoRepository.findAll());
    }

    public Optional<DetalleEstadoDTO> findById(Long id) {
        return detalleEstadoRepository.findById(id)
                .map(detalleEstadoMapper::toDTO);
    }

    public List<DetalleEstadoDTO> findByEjemplarId(Long ejemplarId) {
        return detalleEstadoMapper.toDTO(
                detalleEstadoRepository.findByEjemplarIdOrderByFechaDesc(ejemplarId)
        );
    }

    public List<DetalleEstadoDTO> findByEstadoId(Long estadoId) {
        return detalleEstadoMapper.toDTO(
                detalleEstadoRepository.findByEstadoId(estadoId)
        );
    }

    public DetalleEstadoDTO create(Long ejemplarId, Long estadoId) {
        Ejemplar ejemplar = ejemplarRepository.findById(ejemplarId)
                .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado: " + ejemplarId));

        Estado estado = estadoRepository.findById(estadoId)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado: " + estadoId));

        DetalleEstado detalleEstado = DetalleEstado.builder()
                .fecha(LocalDate.now())
                .ejemplar(ejemplar)
                .estado(estado)
                .build();

        DetalleEstado saved = detalleEstadoRepository.save(detalleEstado);
        return detalleEstadoMapper.toDTO(saved);
    }

    public void delete(Long id) {
        if (!detalleEstadoRepository.existsById(id)) {
            throw new RuntimeException("Detalle de estado no encontrado: " + id);
        }
        detalleEstadoRepository.deleteById(id);
    }

    public DetalleEstadoDTO getEstadoActualEjemplar(Long ejemplarId) {
        List<DetalleEstado> detalles = detalleEstadoRepository
                .findByEjemplarIdOrderByFechaDesc(ejemplarId);

        if (detalles.isEmpty()) {
            throw new RuntimeException("No hay estados registrados para el ejemplar: " + ejemplarId);
        }

        return detalleEstadoMapper.toDTO(detalles.get(0));
    }
}
