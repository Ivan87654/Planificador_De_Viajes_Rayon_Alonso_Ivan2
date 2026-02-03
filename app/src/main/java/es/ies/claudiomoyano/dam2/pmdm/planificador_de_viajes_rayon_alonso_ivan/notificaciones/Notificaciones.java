package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.notificaciones;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.MainActivity;
import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.R;

/**
 Clase encargada de gestionar las notificaciones de la aplicación.

 */
public class Notificaciones {


    public static final String CHANNEL_ID = "canal_app";

    /**
     * Crea el canal de notificaciones

     */
    public static void crearCanal(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence nombre = "Canal principal";
            String descripcion = "Notificaciones de la aplicación";
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, nombre, importancia);
            channel.setDescription(descripcion);

            NotificationManager notificationManager =
                    context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     Envía una notificación cuando se registra un nuevo usuario.
     */
    public static void notificarAltaUsuario(Context context, String usuario) {

        // Intent para abrir la actividad principal al pulsar la notificación
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Construcción de la notificación
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Usuario registrado")
                        .setContentText("Se ha dado de alta: " + usuario)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        // Envío de la notificación
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }

    /**
     Envía una notificación cuando se elimina un viaje.
     */
    public static void notificarBorrado(Context context, String tituloViaje) {

        // Intent para volver a la actividad principal al pulsar la notificación
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Construcción de la notificación
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Registro eliminado")
                        .setContentText("Se ha eliminado el viaje: " + tituloViaje)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        // Envío de la notificación
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);
        notificationManager.notify(2, builder.build());
    }
}
