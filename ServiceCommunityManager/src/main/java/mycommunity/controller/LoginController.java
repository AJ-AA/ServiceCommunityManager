package mycommunity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

@Controller // Indica que esta clase es un controlador de Spring MVC
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Devuelve el nombre de la vista de login
    }

    // Método para manejar errores de login y mostrarlos en la vista
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login"; // Devuelve el nombre de la vista de login
    }
}
