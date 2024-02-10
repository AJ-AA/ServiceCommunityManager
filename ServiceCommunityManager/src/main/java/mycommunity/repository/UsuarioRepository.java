package mycommunity.repository;

import mycommunity.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente
@Repository // Indica que esta interfaz es un repositorio de Spring
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    // Paginación y ordenación
    Page<Usuario> findAll(Pageable pageable);
}
