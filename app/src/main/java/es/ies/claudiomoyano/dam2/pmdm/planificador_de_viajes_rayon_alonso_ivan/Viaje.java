package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

// Clase modelo de datos para representar un viaje

public class Viaje {


    private String titulo;


    private String fechaSalida;


    private int idRecursoImagen;


    private String descripcion;

    // Constructor que inicializa todos los campos del viaje
    public Viaje(String titulo, String fechaSalida, int idRecursoImagen, String descripcion) {
        this.titulo = titulo;
        this.fechaSalida = fechaSalida;
        this.idRecursoImagen = idRecursoImagen;
        this.descripcion = descripcion;
    }

    // Devuelve el título del viaje
    public String getTitulo() {
        return titulo;
    }


    public String getFechaSalida() {
        return fechaSalida;
    }


    public int getIdRecursoImagen() {
        return idRecursoImagen;
    }


    public String getDescripcion() {
        return descripcion;
    }
}