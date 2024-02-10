package mycommunity.service;

import mycommunity.model.Usuario;
import mycommunity.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    // Método para crear o actualizar un usuario
    public Usuario saveOrUpdateUsuario(Usuario usuario) {
        validateUsuario(usuario); // Validación antes de guardar o actualizar
        if (usuario.getId() == null || !usuarioRepository.existsById(usuario.getId())) {
            // Encriptar la contraseña solo si es un nuevo usuario o si se está actualizando la contraseña
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }

    // Método privado para validar un usuario antes de guardar o actualizar
    private void validateUsuario(Usuario usuario) {
        if (!StringUtils.hasText(usuario.getEmail()) || !usuario.getEmail().contains("@")) {
            throw new IllegalArgumentException("El correo electrónico es inválido");
        }
        if (!StringUtils.hasText(usuario.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }
    }

    // Método para obtener todos los usuarios
    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Método para obtener un usuario por ID
    public Optional<Usuario> findUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    // Método para eliminar un usuario
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Método para buscar usuarios por nombre de usuario (username)
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // Puedes agregar más métodos aquí según sea necesario
    public Usuario obtenerUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }
}
