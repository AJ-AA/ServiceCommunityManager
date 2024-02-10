package mycommunity.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {

    public static void main(String[] args) {
        // Crea una instancia de BCryptPasswordEncoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // La contraseña que deseas encriptar
        String rawPassword = "contraseña123";

        // Encripta la contraseña
        String encodedPassword1 = encoder.encode(rawPassword);

        // Imprime la contraseña encriptada
        System.out.println("Contraseña encriptada: " + encodedPassword1);
    }
}
