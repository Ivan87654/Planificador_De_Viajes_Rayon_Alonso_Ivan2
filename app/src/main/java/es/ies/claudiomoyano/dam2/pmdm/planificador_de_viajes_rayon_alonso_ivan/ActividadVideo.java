package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class ActividadVideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        VideoView videoView = findViewById(R.id.videoView);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro_video);
        videoView.setVideoURI(uri);
        videoView.start();

        // Cuando termina el vídeo se va al Login
        videoView.setOnCompletionListener(mp -> {
            Intent intent = new Intent(ActividadVideo.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}