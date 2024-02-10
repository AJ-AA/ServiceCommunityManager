package mycommunity.controller;

import mycommunity.exception.ReservationNotAvailableException;
import mycommunity.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

@ControllerAdvice // Marca la clase como un controlador de consejos para manejar excepciones globalmente
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationNotAvailableException.class) // Maneja esta excepción específica
    @ResponseStatus(HttpStatus.CONFLICT) // Establece el estado HTTP en caso de esta excepción
    public String handleReservaNoDisponibleException(ReservationNotAvailableException e) {
        // Devuelve el mensaje de la excepción
        return e.getMessage();
    }

    @ExceptionHandler(UserNotFoundException.class) // Maneja esta excepción específica
    @ResponseStatus(HttpStatus.NOT_FOUND) // Establece el estado HTTP en caso de esta excepción
    public String handleUsuarioNoEncontradoException(UserNotFoundException e) {
        // Devuelve el mensaje de la excepción
        return e.getMessage();
    }
}
