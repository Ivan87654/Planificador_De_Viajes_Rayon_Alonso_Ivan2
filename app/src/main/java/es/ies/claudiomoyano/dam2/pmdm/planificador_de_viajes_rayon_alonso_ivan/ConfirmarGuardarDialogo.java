package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

// DialogFragment para mostrar un diálogo de confirmación
public class ConfirmarGuardarDialogo extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Crear AlertDialog con Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Título y mensaje desde recursos XML
        builder.setTitle(R.string.dialogo_confirmar_titulo);
        builder.setMessage(R.string.dialogo_confirmar_mensaje);

        // Botón Aceptar
        builder.setPositiveButton(R.string.dialogo_confirmar_aceptar,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        // Botón Cancelar
        builder.setNegativeButton(R.string.dialogo_confirmar_cancelar,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }
}