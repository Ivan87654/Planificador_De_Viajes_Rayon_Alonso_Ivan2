package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    // Nombre y versión de la BD
    private static final String DB_NAME = "planificador_viajes.db";
    private static final int DB_VERSION = 1;

    // Tablas
    public static final String TABLA_USUARIOS = "usuarios";
    public static final String TABLA_VIAJES = "viajes";
    public static final String TABLA_LUGARES = "lugares";
    public static final String TABLA_ACTIVIDADES = "actividades";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(
                "CREATE TABLE " + TABLA_USUARIOS + " (" +
                        "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "usuario TEXT UNIQUE NOT NULL, " +
                        "password TEXT NOT NULL, " +
                        "rol TEXT NOT NULL, " +
                        "telefono_admin TEXT" +
                        ");"
        );


        db.execSQL(
                "CREATE TABLE " + TABLA_VIAJES + " (" +
                        "id_viaje INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "titulo TEXT NOT NULL, " +
                        "fecha TEXT, " +
                        "descripcion TEXT, " +
                        "id_usuario INTEGER NOT NULL, " +
                        "FOREIGN KEY(id_usuario) REFERENCES " + TABLA_USUARIOS + "(id_usuario)" +
                        ");"
        );


        db.execSQL(
                "CREATE TABLE " + TABLA_LUGARES + " (" +
                        "id_lugar INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT NOT NULL, " +
                        "lat REAL, " +
                        "lng REAL, " +
                        "id_viaje INTEGER NOT NULL, " +
                        "FOREIGN KEY(id_viaje) REFERENCES " + TABLA_VIAJES + "(id_viaje)" +
                        ");"
        );
        db.execSQL(
                "CREATE TABLE " + TABLA_ACTIVIDADES + " (" +
                        "id_actividad INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nombre TEXT NOT NULL, " +
                        "tipo TEXT, " +
                        "hora TEXT, " +
                        "id_viaje INTEGER NOT NULL, " +
                        "FOREIGN KEY(id_viaje) REFERENCES " + TABLA_VIAJES + "(id_viaje)" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLA_ACTIVIDADES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_LUGARES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_VIAJES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
        onCreate(db);
    }
}
