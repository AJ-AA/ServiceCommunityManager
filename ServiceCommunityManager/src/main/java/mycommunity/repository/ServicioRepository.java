package mycommunity.repository;

import mycommunity.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

@Repository // Indica que esta interfaz es un repositorio de Spring
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

}
