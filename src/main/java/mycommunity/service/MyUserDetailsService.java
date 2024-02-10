package mycommunity.service;

import mycommunity.model.Usuario;
import mycommunity.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

@Service // Indica que esta clase es un servicio de Spring
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Inyección de dependencia del repositorio de usuarios

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Método para cargar un usuario por su nombre de usuario
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        // Si el usuario es encontrado, se imprime en consola y se retorna un objeto User de Spring Security
        System.out.println("Usuario cargado: " + usuario.getUsername() + " con contraseña: " + usuario.getPassword());
        return new User(usuario.getUsername(), usuario.getPassword(), emptyList()); // La lista vacía representa los roles o autoridades del usuario
    }

}
