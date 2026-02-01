package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FragmentCabecera extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cabecera, container, false);

        TextView txtUsuarioRol = v.findViewById(R.id.txtUsuarioRol);

        if (getArguments() != null) {
            String rol = getArguments().getString("ROL", "user");
            txtUsuarioRol.setText("Usuario: " + rol);
        }

        return v;
    }
}