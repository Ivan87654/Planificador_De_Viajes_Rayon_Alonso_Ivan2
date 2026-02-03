package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**

 * Permite controlar la reproducción mediante botones Play, Pause y Stop.
 */
public class ActividadAudio extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private TextView txtEstado;
    private Button btnPlay, btnPause, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        // Enlace con los elementos de la interfaz
        txtEstado = findViewById(R.id.txtEstadoAudio);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);

        // Inicialización del MediaPlayer con el audio local
        mediaPlayer = MediaPlayer.create(this, R.raw.audio_guia);

        txtEstado.setText("Estado: preparado");


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                txtEstado.setText("Estado: reproduciendo");
            }
        });


        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    txtEstado.setText("Estado: pausado");
                }
            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                txtEstado.setText("Estado: detenido");

                try {
                    mediaPlayer.prepare();
                    txtEstado.setText("Estado: preparado");
                } catch (Exception e) {
                    txtEstado.setText("Estado: error al preparar");
                }
            }
        });
    }

    /**
     * Libera los recursos del MediaPlayer al cerrar la actividad.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}