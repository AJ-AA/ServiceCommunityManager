package mycommunity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

@Configuration // Marca la clase como de configuración de Spring
public class WebConfig implements WebMvcConfigurer {
    // Habilita la configuración personalizada de MVC

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Añade conversores personalizados al registro
        registry.addConverter(String.class, LocalDateTime.class, source -> {
            // Convierte String a LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            return LocalDateTime.parse(source, formatter);
        });
    }
}
