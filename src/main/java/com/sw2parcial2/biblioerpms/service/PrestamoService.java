package com.sw2parcial2.biblioerpms.service;

import com.sw2parcial2.biblioerpms.dto.PrestamoDTO;
import com.sw2parcial2.biblioerpms.dto.input.CreateDetallePrestamoInput;
import com.sw2parcial2.biblioerpms.dto.input.CreatePrestamoInput;
import com.sw2parcial2.biblioerpms.entity.DetallePrestamo;
import com.sw2parcial2.biblioerpms.entity.Ejemplar;
import com.sw2parcial2.biblioerpms.entity.Miembro;
import com.sw2parcial2.biblioerpms.entity.Prestamo;
import com.sw2parcial2.biblioerpms.mapper.PrestamoMapper;
import com.sw2parcial2.biblioerpms.repository.EjemplarRepository;
import com.sw2parcial2.biblioerpms.repository.MiembroRepository;
import com.sw2parcial2.biblioerpms.repository.PrestamoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final MiembroRepository miembroRepository;
    private final EjemplarRepository ejemplarRepository;
    private final PrestamoMapper prestamoMapper;

    public Page<PrestamoDTO> findAll(Pageable pageable) {
        return prestamoRepository.findAllWithDetails(pageable)
                .map(prestamoMapper::toDTO);
    }

    public Optional<PrestamoDTO> findById(Long id) {
        return prestamoRepository.findById(id)
                .map(prestamoMapper::toDTO);
    }

    public List<PrestamoDTO> findByMiembroId(Long miembroId) {
        return prestamoMapper.toDTO(prestamoRepository.findByMiembroId(miembroId));
    }

    public List<PrestamoDTO> findPrestamosVencidos() {
        return prestamoMapper.toDTO(prestamoRepository.findPrestamosVencidos(LocalDate.now()));
    }

    public PrestamoDTO create(CreatePrestamoInput input) {
        Miembro miembro = miembroRepository.findById(input.getMiembroId())
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado: " + input.getMiembroId()));

        Prestamo prestamo = prestamoMapper.toEntity(input);
        prestamo.setMiembro(miembro);

        Prestamo saved = prestamoRepository.save(prestamo);

        // Crear detalles del préstamo
        if (input.getDetalles() != null) {
            for (CreateDetallePrestamoInput detalleInput : input.getDetalles()) {
                Ejemplar ejemplar = ejemplarRepository.findById(detalleInput.getEjemplarId())
                        .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado: " + detalleInput.getEjemplarId()));

                if (ejemplar.getStock() < detalleInput.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para el ejemplar: " + ejemplar.getNombre());
                }

                DetallePrestamo detalle = DetallePrestamo.builder()
                        .cantidad(detalleInput.getCantidad())
                        .prestamo(saved)
                        .ejemplar(ejemplar)
                        .build();

                saved.getDetalles().add(detalle);

                // Actualizar stock
                ejemplar.setStock(ejemplar.getStock() - detalleInput.getCantidad());
                ejemplarRepository.save(ejemplar);
            }
        }

        return prestamoMapper.toDTO(prestamoRepository.save(saved));
    }

    public void delete(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado: " + id));

        // Restaurar stock de ejemplares
        if (prestamo.getDetalles() != null) {
            for (DetallePrestamo detalle : prestamo.getDetalles()) {
                Ejemplar ejemplar = detalle.getEjemplar();
                ejemplar.setStock(ejemplar.getStock() + detalle.getCantidad());
                ejemplarRepository.save(ejemplar);
            }
        }

        prestamoRepository.deleteById(id);
    }
}
