package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.db.DBHelper;



public class UsuarioDAO {

    private final DBHelper dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * Inserta un usuario en la BD.
     */
    public boolean registrarUsuario(String usuario, String password, String rol, String telefonoAdmin) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("usuario", usuario);
        values.put("password", password);
        values.put("rol", rol);
        values.put("telefono_admin", telefonoAdmin);

        long resultado = db.insert(DBHelper.TABLA_USUARIOS, null, values);
        db.close();

        return resultado != -1;
    }

    /**
     * Comprueba si existe el usuario con esa contraseña.
     */
    public boolean validarLogin(String usuario, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columnas = {"id_usuario"};
        String selection = "usuario=? AND password=?";
        String[] args = {usuario, password};

        Cursor cursor = db.query(DBHelper.TABLA_USUARIOS, columnas, selection, args, null, null, null);

        boolean ok = cursor.moveToFirst();

        cursor.close();
        db.close();

        return ok;
    }

    /**
     * Devuelve el rol del usuario
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
        db.close();

        return rol;
    }

    /**
     * Obtiene el teléfono guardado en el perfil del administrador.
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
        db.close();

        return telefono;
    }
    }