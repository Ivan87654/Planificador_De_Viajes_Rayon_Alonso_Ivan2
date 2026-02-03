package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.db.DBHelper;

/**
 * Clase DAO para gestionar las operaciones de la tabla Usuarios.
 */
public class UsuarioDAO {

    private final DBHelper dbHelper;

    // El constructor recibe el Contexto para inicializar el Helper de la BD
    public UsuarioDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * Inserta un nuevo registro.
     */
    public boolean registrarUsuario(String usuario, String password, String rol, String telefonoAdmin) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("usuario", usuario);
        values.put("password", password);
        values.put("rol", rol);
        values.put("telefono_admin", telefonoAdmin);


        long resultado = db.insert(DBHelper.TABLA_USUARIOS, null, values);
        return resultado != -1;
    }

    /**
     * Consulta la BD para verificar si las credenciales coinciden.

     */
    public boolean validarLogin(String usuario, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columnas = {"id_usuario"};
        String selection = "usuario=? AND password=?";
        String[] args = {usuario, password};

        // El Cursor almacena el conjunto de resultados de la consulta
        Cursor cursor = db.query(
                DBHelper.TABLA_USUARIOS,
                columnas,
                selection,
                args,
                null,
                null,
                null
        );


        boolean ok = cursor.moveToFirst();
        cursor.close();
        return ok;
    }

    /**
     * Obtiene el valor de la columna 'rol' para un usuario específico.

     */
    public String obtenerRol(String usuario) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columnas = {"rol"};
        String selection = "usuario=?";
        String[] args = {usuario};

        Cursor cursor = db.query(DBHelper.TABLA_USUARIOS, columnas, selection, args, null, null, null);

        String rol = null;
        if (cursor.moveToFirst()) {
            rol = cursor.getString(0);
        }
        cursor.close();
        return rol;
    }

    /**
     * Busca el teléfono del administrador.
     */
    public String obtenerTelefonoAdmin() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columnas = {"telefono_admin"};
        String selection = "rol=?";
        String[] args = {"admin"};

        Cursor cursor = db.query(DBHelper.TABLA_USUARIOS, columnas, selection, args, null, null, null);

        String telefono = null;
        if (cursor.moveToFirst()) {
            telefono = cursor.getString(0);
        }
        cursor.close();
        return telefono;
    }

    /**
     * Recupera el ID único del usuario.
     */
    public int obtenerIdUsuario(String usuario) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columnas = {"id_usuario"};
        String selection = "usuario=?";
        String[] args = {usuario};

        Cursor cursor = db.query(DBHelper.TABLA_USUARIOS, columnas, selection, args, null, null, null);

        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return id;
    }
}