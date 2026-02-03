package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan.Viaje;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adaptador personalizado que conecta la lista de Viajes con el RecyclerView

public class AdaptadorViajes extends RecyclerView.Adapter<AdaptadorViajes.ViajeViewHolder> {

    // Listener para pulsaciones simples sobre un elemento de la lista

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Listener para acciones del menú contextual
    public interface OnItemMenuListener {
        void onEliminar(int position);
    }

    private List<Viaje> listaViajes;
    private OnItemClickListener listener;
    private OnItemMenuListener menuListener;

    // Constructor que recibe la lista de viajes
    public AdaptadorViajes(List<Viaje> listaViajes) {
        this.listaViajes = listaViajes;
    }


    //Metodo para asignar el listener de clic

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    // Metodo para asignar el listener del menu contextual
    public void setOnItemMenuListener(OnItemMenuListener menuListener) {
        this.menuListener = menuListener;
    }

    @NonNull
    @Override
    public ViajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflar el layout XML del ítem
        View vistaItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_viaje, parent, false);

        return new ViajeViewHolder(vistaItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViajeViewHolder holder, int position) {

        // Obtener el viaje correspondiente a la posición
        Viaje viaje = listaViajes.get(position);

        // Asignar los valores a los componentes visuales de la tarjeta
        holder.txtTitulo.setText(viaje.getTitulo());
        holder.txtFecha.setText(viaje.getFechaSalida());
        holder.imgPortada.setImageResource(viaje.getIdRecursoImagen());
    }

    @Override
    public int getItemCount() {
        return listaViajes.size();   // Tamaño total de la lista
    }

    // Clase interna ViewHolder que referencia los elementos visuales del ítem

    public class ViajeViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPortada;
        TextView txtTitulo;
        TextView txtFecha;

        public ViajeViewHolder(@NonNull View itemView) {
            super(itemView);

            // Asociar los componentes del XML con Java
            imgPortada = itemView.findViewById(R.id.img_portada_item);
            txtTitulo = itemView.findViewById(R.id.txt_titulo_item);
            txtFecha = itemView.findViewById(R.id.txt_fecha_item);

            // Evento de clic sobre un item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        int posicion = getAdapterPosition();

                        // Comprobar que la posición es válida
                        if (posicion != RecyclerView.NO_POSITION) {
                            listener.onItemClick(posicion);
                        }
                    }
                }
            });

            // Evento de clic largo para mostrar menú contextual

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if (menuListener == null) return false;

                    int posicion = getAdapterPosition();
                    if (posicion == RecyclerView.NO_POSITION) return false;

                    // Crear menú contextual
                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    popup.getMenuInflater().inflate(R.menu.menu_viaje_contextual, popup.getMenu());

                    // Acciones de cada opción del menú
                    popup.setOnMenuItemClickListener(item -> {
                        if (item.getItemId() == R.id.opcion_eliminar_viaje) {
                            menuListener.onEliminar(posicion);
                            return true;
                        }
                        return false;
                    });

                    popup.show();
                    return true;
                }
            });
        }
    }
}
