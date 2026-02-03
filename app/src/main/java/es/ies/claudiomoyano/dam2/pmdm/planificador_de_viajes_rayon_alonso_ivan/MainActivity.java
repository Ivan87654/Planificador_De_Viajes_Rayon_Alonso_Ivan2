package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.dao.UsuarioDAO;
import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.dao.ViajeDAO;
import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.notificaciones.Notificaciones;
import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.servicios.ServicioBateriaSMS;

/**
 * Actividad principal de la aplicación.
 */
public class MainActivity extends AppCompatActivity {

    // RecyclerView que muestra los viajes
    private RecyclerView rvViajes;

    // Adaptador que conecta los datos con el RecyclerView
    private AdaptadorViajes adaptadorViajes;

    // Lista de viajes mostrados en pantalla
    private List<Viaje> listaViajes;

    // Código para identificar el resultado de crear un nuevo viaje
    private static final int CODIGO_NUEVO_VIAJE = 1;

    // Datos del usuario logueado
    private String rol = "user";
    private String usuarioActual = null;
    private int idUsuario = -1;

    // DAOs para acceso a la base de datos
    private UsuarioDAO usuarioDAO;
    private ViajeDAO viajeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recibir datos enviados desde LoginActivity
        Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra("ROL")) {
                rol = i.getStringExtra("ROL");
            }
            if (i.hasExtra("USUARIO")) {
                usuarioActual = i.getStringExtra("USUARIO");
            }
        }

        // Inicializar acceso a la base de datos
        usuarioDAO = new UsuarioDAO(this);
        viajeDAO = new ViajeDAO(this);

        // Obtener el id del usuario para relacionar los viajes
        if (usuarioActual != null) {
            idUsuario = usuarioDAO.obtenerIdUsuario(usuarioActual);
        }

        // Cargar el fragmento de cabecera mostrando el rol
        FragmentCabecera cabecera = new FragmentCabecera();
        Bundle bundle = new Bundle();
        bundle.putString("ROL", rol);
        cabecera.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorCabecera, cabecera)
                .commit();

        // Configurar RecyclerView
        rvViajes = findViewById(R.id.rv_viajes);
        rvViajes.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar lista de viajes
        listaViajes = new ArrayList<>();

        // Cargar viajes de ejemplo
        cargarViajesEjemplo();

        // Añadir viajes guardados en SQLite para el usuario actual
        if (idUsuario != -1) {
            listaViajes.addAll(viajeDAO.obtenerViajesDeUsuario(idUsuario));
        }

        // Crear y asignar el adaptador
        adaptadorViajes = new AdaptadorViajes(listaViajes);
        rvViajes.setAdapter(adaptadorViajes);

        // abrir detalle del viaje
        adaptadorViajes.setOnItemClickListener(position -> {
            Viaje viajeSeleccionado = listaViajes.get(position);

            Intent intent = new Intent(MainActivity.this, ActividadDetalleViaje.class);
            intent.putExtra("titulo", viajeSeleccionado.getTitulo());
            intent.putExtra("fecha", viajeSeleccionado.getFechaSalida());
            intent.putExtra("imagen", viajeSeleccionado.getIdRecursoImagen());
            intent.putExtra("descripcion", viajeSeleccionado.getDescripcion());

            startActivity(intent);
        });

        // Opción eliminar:
        adaptadorViajes.setOnItemMenuListener(position -> {

            Viaje v = listaViajes.get(position);

            // Si el viaje está en la base de datos, eliminarlo también allí
            if (v.getIdViaje() != -1) {
                viajeDAO.eliminarViaje(v.getIdViaje());
            }

            listaViajes.remove(position);
            adaptadorViajes.notifyItemRemoved(position);

            // Mostrar notificación de borrado
            Notificaciones.notificarBorrado(MainActivity.this, v.getTitulo());
        });

        // Iniciar servicio que controla la batería y envía SMS
        iniciarServicioBateriaSMS();
    }

    /**
     * Inicia el servicio de batería si el permiso de SMS está concedido.
     */
    private void iniciarServicioBateriaSMS() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(this, ServicioBateriaSMS.class);
            startService(intent);
        }
    }

    /**
     * Añade viajes de ejemplo a la lista.
     */
    private void cargarViajesEjemplo() {
        listaViajes.add(new Viaje("Roma - El Coliseum", "12/04/2025", R.drawable.roma,
                "Visita una de las Siete Maravillas del Mundo Moderno."));
        listaViajes.add(new Viaje("París - La Torre Eiffel", "20/07/2025", R.drawable.paris,
                "La Torre Eiffel es el monumento más famoso de Francia."));
        listaViajes.add(new Viaje("Londres - El Big Ben", "05/10/2025", R.drawable.londres,
                "Uno de los iconos más reconocibles del Reino Unido."));
        listaViajes.add(new Viaje("Nueva York – Manhattan", "15/03/2026", R.drawable.nuevayork,
                "El corazón de la ciudad que nunca duerme."));
        listaViajes.add(new Viaje("Berlín – Ruta histórica", "09/09/2025", R.drawable.berlin,
                "Una ciudad marcada por la historia europea."));
        listaViajes.add(new Viaje("Egipto – Pirámides de Giza", "02/02/2026", R.drawable.egipto,
                "Restos milenarios del Antiguo Egipto."));
    }

    /**
     * Carga el menú según el rol del usuario.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if ("admin".equals(rol)) {
            getMenuInflater().inflate(R.menu.menu_admin, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_usuario, menu);
        }
        return true;
    }

    /**
     * Gestiona las acciones del menú.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.opcion_nuevo_viaje) {
            startActivityForResult(new Intent(this, ActividadNuevoViaje.class), CODIGO_NUEVO_VIAJE);
            return true;

        } else if (id == R.id.opcion_acerca_de) {
            startActivity(new Intent(this, ActividadAcercaDe.class));
            return true;

        } else if (id == R.id.opcion_contactos) {
            startActivity(new Intent(this, ActividadContactos.class));
            return true;

        } else if (id == R.id.opcion_ajustes) {
            startActivity(new Intent(this, ActividadPreferencias.class));
            return true;

        } else if (id == R.id.opcion_mapa) {
            startActivity(new Intent(this, ActividadMapa.class));
            return true;

        } else if (id == R.id.opcion_video) {
            Intent intent = new Intent(this, ActividadVideo.class);
            intent.putExtra("DESDE_MENU", true);
            startActivity(intent);
            return true;

        } else if (id == R.id.opcion_audio) {
            startActivity(new Intent(this, ActividadAudio.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Recibe el viaje creado y lo guarda en SQLite y en la lista.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_NUEVO_VIAJE && resultCode == RESULT_OK && data != null) {

            String titulo = data.getStringExtra("titulo");
            String fecha = data.getStringExtra("fecha");
            String descripcion = data.getStringExtra("descripcion");

            int imagen = R.drawable.viaje;

            // Guardar el viaje en la base de datos
            if (idUsuario != -1) {
                long idViaje = viajeDAO.insertarViaje(titulo, fecha, descripcion, idUsuario);
                listaViajes.add(new Viaje((int) idViaje, titulo, fecha, imagen, descripcion));
            } else {
                // Si no hay usuario, se añade solo en memoria
                listaViajes.add(new Viaje(titulo, fecha, imagen, descripcion));
            }

            adaptadorViajes.notifyItemInserted(listaViajes.size() - 1);
        }
    }
}