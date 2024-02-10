package mycommunity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    // Constructores, getters y setters
    public Reserva() {
    }

    public Reserva(Long id, Usuario usuario, Servicio servicio, LocalDateTime fechaHora) {
        this.id = id;
        this.usuario = usuario;
        this.servicio = servicio;
        this.fechaHora = fechaHora;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", servicio=" + servicio +
                ", fechaHora=" + fechaHora +
                '}';
    }
}
