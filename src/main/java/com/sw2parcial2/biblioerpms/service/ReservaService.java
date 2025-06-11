package com.sw2parcial2.biblioerpms.service;

import com.sw2parcial2.biblioerpms.dto.ReservaDTO;
import com.sw2parcial2.biblioerpms.dto.input.CreateDetalleReservaInput;
import com.sw2parcial2.biblioerpms.dto.input.CreateReservaInput;
import com.sw2parcial2.biblioerpms.entity.DetalleReserva;
import com.sw2parcial2.biblioerpms.entity.Ejemplar;
import com.sw2parcial2.biblioerpms.entity.Miembro;
import com.sw2parcial2.biblioerpms.entity.Reserva;
import com.sw2parcial2.biblioerpms.mapper.ReservaMapper;
import com.sw2parcial2.biblioerpms.repository.EjemplarRepository;
import com.sw2parcial2.biblioerpms.repository.MiembroRepository;
import com.sw2parcial2.biblioerpms.repository.ReservaRepository;
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
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final MiembroRepository miembroRepository;
    private final EjemplarRepository ejemplarRepository;
    private final ReservaMapper reservaMapper;

    public Page<ReservaDTO> findAll(Pageable pageable) {
        return reservaRepository.findAllWithDetails(pageable)
                .map(reservaMapper::toDTO);
    }

    public Optional<ReservaDTO> findById(Long id) {
        return reservaRepository.findById(id)
                .map(reservaMapper::toDTO);
    }

    public List<ReservaDTO> findByMiembroId(Long miembroId) {
        return reservaMapper.toDTO(reservaRepository.findByMiembroId(miembroId));
    }

    public List<ReservaDTO> findReservasParaHoy() {
        return reservaMapper.toDTO(reservaRepository.findReservasParaHoy(LocalDate.now()));
    }

    public ReservaDTO create(CreateReservaInput input) {
        Miembro miembro = miembroRepository.findById(input.getMiembroId())
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado: " + input.getMiembroId()));

        Reserva reserva = Reserva.builder()
                .fechaRegistro(input.getFechaRegistro())
                .fechaRecojo(input.getFechaRecojo())
                .miembro(miembro)
                .build();

        Reserva saved = reservaRepository.save(reserva);

        // Crear detalles de la reserva
        if (input.getDetalles() != null) {
            for (CreateDetalleReservaInput detalleInput : input.getDetalles()) {
                Ejemplar ejemplar = ejemplarRepository.findById(detalleInput.getEjemplarId())
                        .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado: " + detalleInput.getEjemplarId()));

                if (ejemplar.getStock() < detalleInput.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para reservar el ejemplar: " + ejemplar.getNombre());
                }

                DetalleReserva detalle = DetalleReserva.builder()
                        .cantidad(detalleInput.getCantidad())
                        .reserva(saved)
                        .ejemplar(ejemplar)
                        .build();

                saved.getDetalles().add(detalle);

                // Reducir stock temporalmente (se puede implementar lógica específica)
                // Por ahora solo validamos que haya stock disponible
            }
        }

        return reservaMapper.toDTO(reservaRepository.save(saved));
    }

    public void delete(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Reserva no encontrada: " + id);
        }
        reservaRepository.deleteById(id);
    }

    public ReservaDTO convertirReservaAPrestamo(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada: " + reservaId));

        // Lógica para convertir reserva a préstamo
        // Esta funcionalidad se puede expandir según necesidades específicas

        return reservaMapper.toDTO(reserva);
    }
}
