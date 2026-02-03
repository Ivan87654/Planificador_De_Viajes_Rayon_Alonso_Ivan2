package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

public class Viaje {

    private int idViaje;
    private String titulo;
    private String fechaSalida;
    private int idRecursoImagen;
    private String descripcion;

    public Viaje(String titulo, String fechaSalida, int idRecursoImagen, String descripcion) {
        this.idViaje = -1;
        this.titulo = titulo;
        this.fechaSalida = fechaSalida;
        this.idRecursoImagen = idRecursoImagen;
        this.descripcion = descripcion;
    }

    public Viaje(int idViaje, String titulo, String fechaSalida, int idRecursoImagen, String descripcion) {
        this.idViaje = idViaje;
        this.titulo = titulo;
        this.fechaSalida = fechaSalida;
        this.idRecursoImagen = idRecursoImagen;
        this.descripcion = descripcion;
    }

    public int getIdViaje() { return idViaje; }
    public String getTitulo() { return titulo; }
    public String getFechaSalida() { return fechaSalida; }
    public int getIdRecursoImagen() { return idRecursoImagen; }
    public String getDescripcion() { return descripcion; }
}