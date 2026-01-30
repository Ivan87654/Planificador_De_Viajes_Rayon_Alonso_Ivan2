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


public class ServicioBateriaSMS extends Service {

    private BroadcastReceiver receptorBateria;
    private boolean smsEnviado = false; // para no enviar varios SMS seguidos

    @Override
    public void onCreate() {
        super.onCreate();

        receptorBateria = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                if (level == -1 || scale == -1) return;

                int porcentaje = (int) ((level * 100.0f) / scale);

                // Si batería <= 5% y no se ha enviado ya
                if (porcentaje <= 5 && !smsEnviado) {
                    smsEnviado = true;

                    UsuarioDAO usuarioDAO = new UsuarioDAO(context);
                    String telefonoAdmin = usuarioDAO.obtenerTelefonoAdmin();

                    if (telefonoAdmin != null && !telefonoAdmin.trim().isEmpty()) {
                        String mensaje = "URGENTE: Batería por debajo del 5% (" + porcentaje + "%).";

                        // Envío de SMS con SmsManager
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(telefonoAdmin, null, mensaje, null, null);

                        Toast.makeText(context, "SMS enviado al admin", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "No hay teléfono de admin guardado", Toast.LENGTH_SHORT).show();
                    }


                    stopSelf();
                }
            }
        };

        // Escuchar cambios de batería
        registerReceiver(receptorBateria, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receptorBateria != null) {
            unregisterReceiver(receptorBateria);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Mantener el servicio activo hasta que se cumpla la condición
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}