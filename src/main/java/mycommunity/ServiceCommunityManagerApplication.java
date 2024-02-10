package mycommunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

@SpringBootApplication
@EntityScan(basePackages = "mycommunity.model")
public class ServiceCommunityManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCommunityManagerApplication.class, args);
    }
}
