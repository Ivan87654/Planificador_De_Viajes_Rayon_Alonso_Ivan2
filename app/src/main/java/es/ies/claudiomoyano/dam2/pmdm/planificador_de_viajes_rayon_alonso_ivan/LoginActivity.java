package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.appcompat.app.AppCompatActivity;

import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.dao.UsuarioDAO;
import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.notificaciones.Notificaciones;

/**
 * - Permite registrar usuarios en SQLite y validar el acceso.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText edtUsuario, edtPassword, edtTelefonoAdmin;
    private CheckBox chkAdmin;
    private Button btnEntrar, btnRegistrar;

    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Notificaciones.crearCanal(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 200);
            }
        }

        usuarioDAO = new UsuarioDAO(this);

        edtUsuario = findViewById(R.id.edtUsuario);
        edtPassword = findViewById(R.id.edtPassword);
        edtTelefonoAdmin = findViewById(R.id.edtTelefonoAdmin);
        chkAdmin = findViewById(R.id.chkAdmin);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Activar/desactivar el teléfono admin según checkbox
        chkAdmin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            edtTelefonoAdmin.setEnabled(isChecked);
            if (!isChecked) edtTelefonoAdmin.setText("");
        });

        btnRegistrar.setOnClickListener(v -> registrar());
        btnEntrar.setOnClickListener(v -> entrar());
    }

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

        boolean ok = usuarioDAO.registrarUsuario(usuario, pass, rol, esAdmin ? telAdmin : null);
        if (ok) {
            Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();


            Notificaciones.notificarAltaUsuario(this, usuario);

        } else {
            Toast.makeText(this, "No se pudo registrar (¿usuario repetido?)", Toast.LENGTH_SHORT).show();
        }
    }

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

        String rol = usuarioDAO.obtenerRol(usuario);

        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("ROL", rol);
        startActivity(i);
        finish();
    }
}