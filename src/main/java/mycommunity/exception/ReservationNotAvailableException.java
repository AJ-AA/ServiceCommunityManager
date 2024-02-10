package mycommunity.exception;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

public class ReservationNotAvailableException extends RuntimeException {

    // Constructor que acepta un mensaje de error para la excepción
    public ReservationNotAvailableException(String mensaje) {
        super(mensaje);
    }
}
