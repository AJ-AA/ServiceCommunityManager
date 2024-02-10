package mycommunity.service;

import mycommunity.dto.ServiceDTO;
import mycommunity.model.Servicio;
import mycommunity.repository.ReservaRepository;
import mycommunity.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;
    private final ReservaRepository reservaRepository;

    @Autowired
    public ServicioService(ServicioRepository servicioRepository, ReservaRepository reservaRepository) {
        this.servicioRepository = servicioRepository;
        this.reservaRepository = reservaRepository;
    }

    // Método para obtener todos los servicios con su disponibilidad actual
    public List<ServiceDTO> findAllServiciosConDisponibilidad() {
        List<Servicio> servicios = servicioRepository.findAll();
        List<ServiceDTO> serviciosConDisponibilidad = new ArrayList<>();

        for (Servicio servicio : servicios) {
            long reservasActuales = reservaRepository.countByServicioId(servicio.getId());
            int disponibilidadActual = servicio.getCapacidad() - (int) reservasActuales;
            serviciosConDisponibilidad.add(new ServiceDTO(servicio, disponibilidadActual));
        }

        return serviciosConDisponibilidad;
    }

    // Método para verificar si hay disponibilidad para un servicio en particular en una fecha y hora específicas
    public boolean verificarDisponibilidad(Long servicioId, LocalDateTime fechaHora) {
        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));
        long reservasActuales = reservaRepository.countByServicioIdAndFechaHora(servicioId, fechaHora);
        return reservasActuales < servicio.getCapacidad();
    }

}
