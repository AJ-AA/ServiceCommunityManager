package mycommunity.repository;

import mycommunity.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente
@Repository // Indica que esta interfaz es un repositorio de Spring
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Encuentra reservas por el ID del servicio y la fecha y hora específicas.
    @Query("SELECT r FROM Reserva r WHERE r.servicio.id = :servicioId AND r.fechaHora = :fechaHora")
    List<Reserva> findByServicioIdAndFechaHora(@Param("servicioId") Long servicioId, @Param("fechaHora") LocalDateTime fechaHora);

    // Sobrecarga del método findAll para soportar paginación.
    Page<Reserva> findAll(Pageable pageable);

    // Cuenta el número de reservas para un servicio en una fecha y hora específicas.
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.servicio.id = :servicioId AND r.fechaHora = :fechaHora")
    long countByServicioIdAndFechaHora(@Param("servicioId") Long servicioId, @Param("fechaHora") LocalDateTime fechaHora);

    // Cuenta el número total de reservas para un servicio.
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.servicio.id = :servicioId")
    long countByServicioId(@Param("servicioId") Long servicioId);
}
