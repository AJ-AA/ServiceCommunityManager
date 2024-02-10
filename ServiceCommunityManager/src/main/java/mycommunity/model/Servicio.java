package mycommunity.model;

import javax.persistence.*;
import java.util.Set;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

@Entity
@Table(name = "servicio")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre", nullable = false)
    private String nombre;

    @Column(name="capacidad", nullable = false)
    private Integer capacidad;

    @OneToMany(mappedBy = "servicio", fetch = FetchType.LAZY)
    private Set<Reserva> reservas;

    // Constructores
    public Servicio() {
    }

    public Servicio(Long id, String nombre, Integer capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }

    // Método para calcular la disponibilidad
    @Transient
    public int getDisponibilidad() {
        return capacidad - (reservas != null ? reservas.size() : 0);
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", capacidad=" + capacidad +
                '}';
    }
}
