package mycommunity.dto;

import mycommunity.model.Servicio;

//NP 141350 Antonio Jose Arenal Armesto
//Feedback Final Programacion Concurrente

public class ServiceDTO {
    private Long id;
    private String nombre;
    private Integer disponibilidad;

    // Constructor que acepta un objeto Servicio y su disponibilidad actual
    public ServiceDTO(Servicio servicio, int disponibilidadActual) {
        this.id = servicio.getId();
        this.nombre = servicio.getNombre();
        this.disponibilidad = disponibilidadActual;
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

    public Integer getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Integer disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    // Método toString para representar el DTO como un string
    @Override
    public String toString() {
        return "ServicioDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", disponibilidad=" + disponibilidad +
                '}';
    }
}
