package mycommunity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

@Configuration // Indica que esta clase es de configuración de Spring
@EnableWebSecurity // Habilita la configuración de seguridad web en Spring
public class SecurityConfig {
    // Declaración de variables y beans necesarios para la configuración de seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authz -> authz
                        .antMatchers("/", "/home", "/login").permitAll()
                        .antMatchers("/reservas/**", "/crear-reserva/**").authenticated()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/reservas", true).permitAll()
                        .permitAll()) //
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());
        return http.build(); // Construye y retorna la configuración de seguridad
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Método para encriptar contraseñas
    }
}
