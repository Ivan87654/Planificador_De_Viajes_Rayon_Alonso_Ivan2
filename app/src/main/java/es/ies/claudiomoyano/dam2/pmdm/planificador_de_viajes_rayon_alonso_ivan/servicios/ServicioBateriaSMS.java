package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.servicios;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.dao.UsuarioDAO;

/**
 Servicio que monitoriza el nivel de batería del dispositivo.

 */
public class ServicioBateriaSMS extends Service {


    private BroadcastReceiver receptorBateria;

    // Evita enviar el SMS más de una vez
    private boolean smsEnviado = false;

    @Override
    public void onCreate() {
        super.onCreate();

        // Se crea el BroadcastReceiver que recibe los cambios de batería
        receptorBateria = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                // Si no se puede obtener el nivel de batería, se sale
                if (level == -1 || scale == -1) return;

                // Cálculo del porcentaje de batería
                int porcentaje = (int) ((level * 100.0f) / scale);

                // Si la batería es menor o igual al 5% y no se ha enviado el SMS
                if (porcentaje <= 5 && !smsEnviado) {
                    smsEnviado = true;

                    // Obtener el teléfono del administrador desde SQLite
                    UsuarioDAO usuarioDAO = new UsuarioDAO(context);
                    String telefonoAdmin = usuarioDAO.obtenerTelefonoAdmin();

                    if (telefonoAdmin != null && !telefonoAdmin.trim().isEmpty()) {
                        String mensaje =
                                "URGENTE: Batería por debajo del 5% (" + porcentaje + "%).";

                        // Envío del SMS utilizando SmsManager
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(
                                telefonoAdmin,
                                null,
                                mensaje,
                                null,
                                null
                        );

                        Toast.makeText(
                                context,
                                "SMS enviado al admin",
                                Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        Toast.makeText(
                                context,
                                "No hay teléfono de admin guardado",
                                Toast.LENGTH_SHORT
                        ).show();
                    }


                    stopSelf();
                }
            }
        };

        // Se registra el receptor para escuchar cambios en la batería
        registerReceiver(
                receptorBateria,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        );
    }

    /**
     Se ejecuta cuando el servicio se destruye.

     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receptorBateria != null) {
            unregisterReceiver(receptorBateria);
        }
    }

    /**
     Indica cómo debe comportarse el servicio si el sistema lo finaliza.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    /**
     Servicio no vinculado, no se permite enlace desde otras clases.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}