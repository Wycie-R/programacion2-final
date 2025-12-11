package com.example.controldeposito.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton; // Nuevo
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.controldeposito.R;
import com.example.controldeposito.model.Producto;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<Producto> productos = new ArrayList<>();
    private OnItemClickListener listener; // Listener para avisar a la Activity

    // Interfaz para comunicar clics
    public interface OnItemClickListener {
        void onEditClick(Producto producto);
        void onDeleteClick(Producto producto);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto current = productos.get(position);
        holder.tvNombre.setText(current.nombre);
        holder.tvCantidad.setText("Stock: " + current.cantidad);
        holder.tvDescripcion.setText(current.descripcion);

        if (current.imagenPath != null && !current.imagenPath.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(new File(current.imagenPath))
                    .centerCrop()
                    .into(holder.ivFoto);
        } else {
            holder.ivFoto.setImageResource(android.R.drawable.ic_menu_camera);
        }

        // Configurar clics de botones
        holder.btnEditar.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(current);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(current);
        });
    }

    @Override
    public int getItemCount() { return productos.size(); }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        notifyDataSetChanged();
    }

    static class ProductoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre, tvCantidad, tvDescripcion;
        private ImageView ivFoto;
        private ImageButton btnEditar, btnEliminar; // Nuevos

        public ProductoViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            ivFoto = itemView.findViewById(R.id.ivProductoFoto);
            btnEditar = itemView.findViewById(R.id.btnEditar);     // Nuevo
            btnEliminar = itemView.findViewById(R.id.btnEliminar); // Nuevo
        }
    }
}