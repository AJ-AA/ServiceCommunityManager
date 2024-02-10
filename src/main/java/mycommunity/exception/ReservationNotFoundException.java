package mycommunity.exception;

import org.springframework.dao.EmptyResultDataAccessException;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

public class ReservationNotFoundException extends Throwable {
    // Constructor que acepta un mensaje y la excepción original
    public ReservationNotFoundException(String s, EmptyResultDataAccessException e) {
        super(s, e);
    }
}
