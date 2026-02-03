package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Actividad que muestra la lista de contactos del dispositivo.

 */
public class ActividadContactos extends AppCompatActivity {

    private ListView listaContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        // Enlace con el ListView del layout
        listaContactos = findViewById(R.id.listaContactos);

        // Lista donde se almacenan los nombres de los contactos
        ArrayList<String> nombres = new ArrayList<>();

        // Consulta al Content Provider de contactos del sistema
        Cursor cursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        );

        if (cursor != null) {
            // Recorrido del cursor para obtener los nombres
            while (cursor.moveToNext()) {
                String nombre = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                ContactsContract.Contacts.DISPLAY_NAME
                        )
                );
                nombres.add(nombre);
            }
            cursor.close();
        } else {

            Toast.makeText(
                    this,
                    "No se pudieron leer contactos",
                    Toast.LENGTH_SHORT
            ).show();
        }

        // Adaptador para mostrar los contactos en el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                nombres
        );

        listaContactos.setAdapter(adapter);
    }
}