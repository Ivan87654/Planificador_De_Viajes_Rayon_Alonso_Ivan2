package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.R;
import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.Viaje;
import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.db.DBHelper;

/**
 * DAO encargado de gestionar los viajes en la base de datos SQLite.
 */
public class ViajeDAO {

    // Helper para acceder a la base de datos
    private final DBHelper dbHelper;

    // Constructor que inicializa el DBHelper
    public ViajeDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * Inserta un viaje en la base de datos y devuelve el id generado.
     */
    public long insertarViaje(String titulo, String fecha, String descripcion, int idUsuario) {
        // Abrir la base de datos
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Valores a insertar en la tabla viajes
        ContentValues values = new ContentValues();
        values.put("titulo", titulo);
        values.put("fecha", fecha);
        values.put("descripcion", descripcion);
        values.put("id_usuario", idUsuario);

        // Insertar el viaje y obtener su id
        long idViaje = db.insert(DBHelper.TABLA_VIAJES, null, values);

        // Crear registros relacionados para demostrar las relaciones entre tablas
        if (idViaje != -1) {
            insertarLugarPorDefecto(db, (int) idViaje);
            insertarActividadPorDefecto(db, (int) idViaje);
        }

        // Cerrar la base de datos
        db.close();
        return idViaje;
    }

    /**
     * Devuelve todos los viajes asociados a un usuario.
     */
    public List<Viaje> obtenerViajesDeUsuario(int idUsuario) {
        // Abrir base de datos
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Viaje> lista = new ArrayList<>();

        // Columnas que se quieren consultar
        String[] columnas = {"id_viaje", "titulo", "fecha", "descripcion"};

        // Condición de búsqueda por usuario
        String selection = "id_usuario=?";
        String[] args = {String.valueOf(idUsuario)};

        // Ejecutar la consulta
        Cursor cursor = db.query(
                DBHelper.TABLA_VIAJES,
                columnas,
                selection,
                args,
                null,
                null,
                "id_viaje DESC"
        );

        // Recorrer los resultados y crear objetos Viaje
        while (cursor.moveToNext()) {
            int idViaje = cursor.getInt(0);
            String titulo = cursor.getString(1);
            String fecha = cursor.getString(2);
            String descripcion = cursor.getString(3);

            // Se usa una imagen por defecto
            lista.add(new Viaje(idViaje, titulo, fecha, R.drawable.viaje, descripcion));
        }

        // Cerrar cursor y base de datos
        cursor.close();
        db.close();

        return lista;
    }

    /**
     * Elimina un viaje de la base de datos a partir de su id.
     */
    public boolean eliminarViaje(int idViaje) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Borrar el registro correspondiente
        int filas = db.delete(
                DBHelper.TABLA_VIAJES,
                "id_viaje=?",
                new String[]{String.valueOf(idViaje)}
        );

        db.close();
        return filas > 0;
    }

    /**
     * Inserta un lugar por defecto asociado a un viaje.
     */
    private void insertarLugarPorDefecto(SQLiteDatabase db, int idViaje) {
        ContentValues values = new ContentValues();
        values.put("nombre", "Lugar principal");
        values.put("lat", 0);
        values.put("lng", 0);
        values.put("id_viaje", idViaje);

        db.insert(DBHelper.TABLA_LUGARES, null, values);
    }

    /**
     * Inserta una actividad inicial asociada a un viaje.
     */
    private void insertarActividadPorDefecto(SQLiteDatabase db, int idViaje) {
        ContentValues values = new ContentValues();
        values.put("nombre", "Actividad inicial");
        values.put("tipo", "General");
        values.put("hora", "00:00");
        values.put("id_viaje", idViaje);

        db.insert(DBHelper.TABLA_ACTIVIDADES, null, values);
    }
}