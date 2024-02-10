package mycommunity.service;

import mycommunity.exception.ReservationNotFoundException;
import mycommunity.model.Reserva;
import mycommunity.model.Servicio;
import mycommunity.repository.ReservaRepository;
import mycommunity.repository.ServicioRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

@Service
public class ReservaService {

    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);

    private final ReservaRepository reservaRepository;
    private final ServicioRepository servicioRepository;


    // Método para obtener todas las reservas paginadas
    public Page<Reserva> findAll(Pageable pageable) {
        return reservaRepository.findAll(pageable);
    }

    @Autowired
    public ReservaService(ReservaRepository reservaRepository, ServicioRepository servicioRepository) {
        this.reservaRepository = reservaRepository;
        this.servicioRepository = servicioRepository; // Inicializar en el constructor
    }

    // Método transaccional para crear una reserva
    @Transactional
    public Reserva crearReserva(Reserva reserva) {
        // Verificar la disponibilidad del servicio en la fecha y hora de la reserva
        boolean disponible = verificarDisponibilidad(reserva.getServicio(), reserva.getFechaHora());
        if (!disponible) {
            throw new IllegalStateException("No hay disponibilidad para el servicio en la fecha y hora especificadas.");
        }
        return reservaRepository.save(reserva);
    }

    // Método para verificar la disponibilidad de un servicio en una fecha y hora específicas
    public boolean verificarDisponibilidad(Servicio servicio, LocalDateTime fechaHora) {
        // Obtener todas las reservas existentes para el servicio en la fecha y hora de la nueva reserva
        List<Reserva> reservasExistentes = reservaRepository.findByServicioIdAndFechaHora(servicio.getId(), fechaHora);

        // Verificar la disponibilidad basada en la capacidad del servicio y las reservas existentes
        return reservasExistentes.size() < servicio.getCapacidad();
    }

    // Método para actualizar una reserva existente
    @Transactional
    public Reserva actualizarReserva(Reserva reservaActualizada) {
        Reserva reservaExistente = reservaRepository.findById(reservaActualizada.getId()).orElseThrow(
                () -> new IllegalArgumentException("Reserva no encontrada con ID: " + reservaActualizada.getId()));
        reservaExistente.setFechaHora(reservaActualizada.getFechaHora());
        reservaExistente.setServicio(reservaActualizada.getServicio());
        // El usuario se mantiene
        return reservaRepository.save(reservaExistente);
    }

    // Método para cancelar una reserva por su ID
    public void cancelarReserva(Long id) throws ReservationNotFoundException {
        try {
            reservaRepository.deleteById(id);
            log.info("Cancelando reserva");
        } catch (EmptyResultDataAccessException e) {
            throw new ReservationNotFoundException("La reserva con ID " + id + " no existe.", e);
        }
    }

    // Método privado para validar una reserva antes de crearla o actualizarla
    private void validarReserva(Reserva reserva) {
        if (reserva.getUsuario() == null) {
            throw new IllegalArgumentException("El usuario de la reserva no puede ser nulo");
        }
        if (reserva.getServicio() == null) {
            throw new IllegalArgumentException("El servicio de la reserva no puede ser nulo");
        }
        if (reserva.getFechaHora() == null || reserva.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha y hora de la reserva no son válidas");
        }
        validarDisponibilidadReserva(reserva);
    }

    // Método privado para verificar la disponibilidad de una reserva
    private void validarDisponibilidadReserva(Reserva reserva) {
        // Obtén todas las reservas existentes para el servicio en la fecha y hora de la nueva reserva
        List<Reserva> reservasExistentes = reservaRepository.findByServicioIdAndFechaHora(
                reserva.getServicio().getId(),
                reserva.getFechaHora()
        );

        if (!reservasExistentes.isEmpty()) {
            // Si ya hay reservas para ese servicio en el mismo horario, verifica la capacidad
            long count = reservasExistentes.stream()
                    .filter(r -> r.getFechaHora().isEqual(reserva.getFechaHora()))
                    .count();

            if (count >= reserva.getServicio().getCapacidad()) {
                throw new IllegalStateException("No hay disponibilidad para el servicio seleccionado en la fecha y hora especificadas.");
            }
        }
        // Si no hay reservas existentes o la cantidad de reservas no supera la capacidad, la reserva puede proceder
    }

    // Método para encontrar una reserva por su ID
    public Reserva findReservaById(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con ID: " + id));
    }
}

