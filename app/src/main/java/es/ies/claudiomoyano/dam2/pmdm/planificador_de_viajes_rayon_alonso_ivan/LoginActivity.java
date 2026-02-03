package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.dao.UsuarioDAO;
import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.notificaciones.Notificaciones;

/**
 Activity de login/registro.
 Permite registrar usuarios en SQLite, validar el acceso y obtener el rol..
 */
public class LoginActivity extends AppCompatActivity {


    private EditText edtUsuario, edtPassword, edtTelefonoAdmin;
    private CheckBox chkAdmin;
    private Button btnEntrar, btnRegistrar;


    private UsuarioDAO usuarioDAO;


    private static final int CODIGO_PERMISO_SMS = 300;
    private static final int CODIGO_PERMISO_CONTACTOS = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Solicitar permiso para enviar SMS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    CODIGO_PERMISO_SMS);
        }

        // Crear el canal de notificaciones
        Notificaciones.crearCanal(this);

        // Solicitar permiso para leer contactos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    CODIGO_PERMISO_CONTACTOS);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 200);
            }
        }

        // Inicializar DAO
        usuarioDAO = new UsuarioDAO(this);

        // Enlazar vistas del layout
        edtUsuario = findViewById(R.id.edtUsuario);
        edtPassword = findViewById(R.id.edtPassword);
        edtTelefonoAdmin = findViewById(R.id.edtTelefonoAdmin);
        chkAdmin = findViewById(R.id.chkAdmin);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnRegistrar = findViewById(R.id.btnRegistrar);


        chkAdmin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            edtTelefonoAdmin.setEnabled(isChecked);
            if (!isChecked) edtTelefonoAdmin.setText("");
        });


        btnRegistrar.setOnClickListener(v -> registrar());
        btnEntrar.setOnClickListener(v -> entrar());
    }

    /**
     * Registra un usuario en SQLite.

     */
    private void registrar() {
        String usuario = edtUsuario.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        boolean esAdmin = chkAdmin.isChecked();
        String rol = esAdmin ? "admin" : "user";
        String telAdmin = edtTelefonoAdmin.getText().toString().trim();


        if (usuario.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Rellena usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (esAdmin && telAdmin.isEmpty()) {
            Toast.makeText(this, "El admin debe indicar teléfono", Toast.LENGTH_SHORT).show();
            return;
        }

        // Inserción en la base de datos
        boolean ok = usuarioDAO.registrarUsuario(usuario, pass, rol, esAdmin ? telAdmin : null);
        if (ok) {
            Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();

            // Notificación al registrar un usuario
            Notificaciones.notificarAltaUsuario(this, usuario);

        } else {
            Toast.makeText(this, "No se pudo registrar (¿usuario repetido?)", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Valida el login y entra a la pantalla principal.

     */
    private void entrar() {
        String usuario = edtUsuario.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();

        if (usuario.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Rellena usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean ok = usuarioDAO.validarLogin(usuario, pass);
        if (!ok) {
            Toast.makeText(this, "Login incorrecto", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener rol del usuario autenticado
        String rol = usuarioDAO.obtenerRol(usuario);


        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("ROL", rol);
        i.putExtra("USUARIO", usuario);
        startActivity(i);
        finish();
    }

    /**
     Recoge el resultado de la solicitud de permisos.

     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CODIGO_PERMISO_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso SMS concedido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso SMS denegado", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == CODIGO_PERMISO_CONTACTOS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso contactos concedido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso contactos denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}