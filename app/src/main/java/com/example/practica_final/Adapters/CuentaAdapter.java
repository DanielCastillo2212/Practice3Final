package com.example.practica_final.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica_final.Entities.Cuenta;
import com.example.practica_final.R;

import java.util.List;

public class CuentaAdapter extends RecyclerView.Adapter<CuentaAdapter.CuentaViewHolder> {
    private List<Cuenta> cuentas;
    private OnCuentaClickListener listener;

    public CuentaAdapter(List<Cuenta> cuentas, OnCuentaClickListener listener) {
        this.cuentas = cuentas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CuentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuenta, parent, false);
        return new CuentaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CuentaViewHolder holder, int position) {
        Cuenta cuenta = cuentas.get(position);
        holder.bind(cuenta);
    }

    @Override
    public int getItemCount() {
        return cuentas.size();
    }

    public class CuentaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtNombreCuenta;

        public CuentaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreCuenta = itemView.findViewById(R.id.txt_nombre_cuenta);
            itemView.setOnClickListener(this);
        }

        public void bind(Cuenta cuenta) {
            txtNombreCuenta.setText(cuenta.getNombre());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Cuenta cuenta = cuentas.get(position);
                listener.onCuentaClick(cuenta);
            }
        }
    }

    public interface OnCuentaClickListener {
        void onCuentaClick(Cuenta cuenta);
    }
}


