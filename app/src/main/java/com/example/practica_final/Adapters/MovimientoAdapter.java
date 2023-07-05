package com.example.practica_final.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica_final.Entities.Movimiento;
import com.example.practica_final.R;

import java.util.List;

public class MovimientoAdapter extends RecyclerView.Adapter<MovimientoAdapter.MovimientoViewHolder> {

    private List<Movimiento> movimientos;

    public MovimientoAdapter(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    @NonNull
    @Override
    public MovimientoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movimiento, parent, false);
        return new MovimientoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovimientoViewHolder holder, int position) {
        Movimiento movimiento = movimientos.get(position);
        holder.tvCantidad.setText(String.valueOf(movimiento.getMonto()));
        holder.tvMotivo.setText(movimiento.getMotivo());
        // Aquí puedes configurar los demás elementos de la vista con los datos del movimiento
        holder.tvTipoMovimiento.setText(movimiento.getTipo());
        holder.tvLatitud.setText(String.valueOf(movimiento.getLatitud()));
        holder.tvLongitud.setText(String.valueOf(movimiento.getLongitud()));
        holder.tvUrlImagen.setText(movimiento.getUrlImagen());
    }

    @Override
    public int getItemCount() {
        return movimientos.size();
    }

    static class MovimientoViewHolder extends RecyclerView.ViewHolder {
        TextView tvCantidad;
        TextView tvMotivo;
        TextView tvTipoMovimiento;
        TextView tvLatitud;
        TextView tvLongitud;
        TextView tvUrlImagen;

        public MovimientoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCantidad = itemView.findViewById(R.id.tv_cantidad);
            tvMotivo = itemView.findViewById(R.id.tv_motivo);
            tvTipoMovimiento = itemView.findViewById(R.id.tv_tipo_movimiento);
            tvLatitud = itemView.findViewById(R.id.tv_latitud);
            tvLongitud = itemView.findViewById(R.id.tv_longitud);
            tvUrlImagen = itemView.findViewById(R.id.tv_url_imagen);
        }
    }
}

