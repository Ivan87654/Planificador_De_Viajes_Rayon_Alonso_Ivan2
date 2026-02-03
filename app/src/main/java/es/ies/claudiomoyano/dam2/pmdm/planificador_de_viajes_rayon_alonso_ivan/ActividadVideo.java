package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

/**
 Actividad que reproduce el vídeo de presentación de la aplicación.

 */
public class ActividadVideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Enlace con el VideoView del layout
        VideoView videoView = findViewById(R.id.videoView);

        // Cargar el vídeo desde la carpeta res/raw
        Uri uri = Uri.parse(
                "android.resource://" + getPackageName() + "/" + R.raw.intro_video
        );
        videoView.setVideoURI(uri);
        videoView.start();

        // Comprobar si la actividad se ha abierto desde el menú
        boolean desdeMenu = getIntent().getBooleanExtra("DESDE_MENU", false);


        videoView.setOnCompletionListener(mp -> {
            if (desdeMenu) {

                finish();
            } else {

                Intent intent =
                        new Intent(ActividadVideo.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}