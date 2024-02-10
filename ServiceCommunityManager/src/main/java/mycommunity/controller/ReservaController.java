package mycommunity.controller;

import jakarta.validation.Valid;
import mycommunity.dto.ServiceDTO;
import mycommunity.exception.ReservationNotFoundException;
import mycommunity.model.Reserva;
import mycommunity.model.Usuario;
import mycommunity.service.ReservaService;
import mycommunity.service.ServicioService;
import mycommunity.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

@Controller
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ServicioService servicioService; // Inyecta el ServicioService

    @Autowired
    private UsuarioService usuarioService;

    // Constructor para inyección de dependencias
    @Autowired
    public ReservaController(ReservaService reservaService, UsuarioService usuarioService) {
        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
    }

    // Método para mostrar el formulario de creación de reserva
    @GetMapping("/crear")
    public String mostrarFormularioDeCreacion(Model model) {
        List<ServiceDTO> serviciosConDisponibilidad = servicioService.findAllServiciosConDisponibilidad();
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("serviciosConDisponibilidad", serviciosConDisponibilidad);
        return "crear-reserva";
    }

    // Método para procesar el formulario de creación de reserva
    @PostMapping("/crear")
    public String crearReserva(@Valid @ModelAttribute("reserva") Reserva reserva, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("serviciosConDisponibilidad", obtenerServiciosConDisponibilidad());
            return "crear-reserva";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Usuario> usuarioOptional = usuarioService.findByUsername(username);
        if (!usuarioOptional.isPresent()) {
            model.addAttribute("error", "El usuario especificado no existe.");
            model.addAttribute("serviciosConDisponibilidad", obtenerServiciosConDisponibilidad());
            return "crear-reserva";
        }

        Usuario usuario = usuarioOptional.get();
        reserva.setUsuario(usuario);

        if (!servicioService.verificarDisponibilidad(reserva.getServicio().getId(), reserva.getFechaHora())) {
            model.addAttribute("error", "No hay disponibilidad para el servicio en la fecha y hora seleccionadas.");
            model.addAttribute("serviciosConDisponibilidad", obtenerServiciosConDisponibilidad());
            return "crear-reserva";
        }

        reservaService.crearReserva(reserva);
        redirectAttributes.addFlashAttribute("msgSuccess", "Reserva creada con éxito.");
        return "redirect:/reservas";
    }

    // Método auxiliar para obtener servicios con disponibilidad
    private List<ServiceDTO> obtenerServiciosConDisponibilidad() {
        return servicioService.findAllServiciosConDisponibilidad();
    }

    // Método para cancelar una reserva
    @GetMapping("/cancelar/{id}")
    public String cancelarReserva(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reservaService.cancelarReserva(id);
            redirectAttributes.addFlashAttribute("msgSuccess", "Reserva cancelada con éxito");
        } catch (RuntimeException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (ReservationNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/reservas";
    }

    // Método para listar todas las reservas
    @GetMapping("")
    public String listarReservas(Model model, Pageable pageable) {
        Page<Reserva> paginaReservas = reservaService.findAll(pageable);
        model.addAttribute("paginaReservas", paginaReservas);
        return "reservas";
    }

    // Método para mostrar el formulario de edición de reserva
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Optional<Reserva> reserva = Optional.ofNullable(reservaService.findReservaById(id));
        if (reserva.isPresent()) {
            model.addAttribute("reserva", reserva.get());
            model.addAttribute("serviciosConDisponibilidad", servicioService.findAllServiciosConDisponibilidad());
            return "editar-reserva";
        } else {
            return "redirect:/reservas";
        }
    }

    // Método para procesar el formulario de edición de reserva
    @PostMapping("/editar")
    public String procesarFormularioEdicion(@Valid @ModelAttribute("reserva") Reserva reservaActualizada, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("serviciosConDisponibilidad", servicioService.findAllServiciosConDisponibilidad());
            return "editar-reserva";
        }

        Optional<Reserva> optionalReserva = Optional.ofNullable(reservaService.findReservaById(reservaActualizada.getId()));
        if (!optionalReserva.isPresent()) {
            model.addAttribute("error", "Reserva no encontrada con ID: " + reservaActualizada.getId());
            return "error-page";
        }

        Reserva reservaExistente = optionalReserva.get();
        reservaExistente.setFechaHora(reservaActualizada.getFechaHora());
        reservaExistente.setServicio(reservaActualizada.getServicio());

        reservaService.actualizarReserva(reservaExistente);

        return "redirect:/reservas";
    }
}
