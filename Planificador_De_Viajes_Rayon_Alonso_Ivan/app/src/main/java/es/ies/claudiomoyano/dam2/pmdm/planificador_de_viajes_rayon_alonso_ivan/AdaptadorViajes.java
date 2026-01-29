package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorViajes extends RecyclerView.Adapter<AdaptadorViajes.ViajeViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public interface OnItemMenuListener {
        void onEditar(int position);
        void onEliminar(int position);
    }

    private List<Viaje> listaViajes;
    private OnItemClickListener listener;
    private OnItemMenuListener menuListener;

    public AdaptadorViajes(List<Viaje> listaViajes) {
        this.listaViajes = listaViajes;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemMenuListener(OnItemMenuListener menuListener) {
        this.menuListener = menuListener;
    }

    @NonNull
    @Override
    public ViajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vistaItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_viaje, parent, false);
        return new ViajeViewHolder(vistaItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViajeViewHolder holder, int position) {

        Viaje viaje = listaViajes.get(position);

        holder.txtTitulo.setText(viaje.getTitulo());
        holder.txtFecha.setText(viaje.getFechaSalida());
        holder.imgPortada.setImageResource(viaje.getIdRecursoImagen());
    }

    @Override
    public int getItemCount() {
        return listaViajes.size();
    }


    public class ViajeViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPortada;
        TextView txtTitulo;
        TextView txtFecha;

        public ViajeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPortada = itemView.findViewById(R.id.img_portada_item);
            txtTitulo = itemView.findViewById(R.id.txt_titulo_item);
            txtFecha = itemView.findViewById(R.id.txt_fecha_item);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int posicion = getAdapterPosition();
                        if (posicion != RecyclerView.NO_POSITION) {
                            listener.onItemClick(posicion);
                        }
                    }
                }
            });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (menuListener == null) return false;

                    int posicion = getAdapterPosition();
                    if (posicion == RecyclerView.NO_POSITION) return false;

                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    popup.getMenuInflater().inflate(R.menu.menu_viaje_contextual, popup.getMenu());

                    popup.setOnMenuItemClickListener(item -> {
                        int id = item.getItemId();
                        if (id == R.id.opcion_editar_viaje) {
                            menuListener.onEditar(posicion);
                            return true;
                        } else if (id == R.id.opcion_eliminar_viaje) {
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

