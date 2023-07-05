package com.example.practica_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.practica_final.Adapters.CuentaAdapter;
import com.example.practica_final.DB.AppDatabase;
import com.example.practica_final.Entities.Cuenta;
import com.example.practica_final.Services.CuentaDao;

import java.util.List;
import java.util.concurrent.Executors;

public class ListaCuentasActivity extends AppCompatActivity implements CuentaAdapter.OnCuentaClickListener {
    private RecyclerView recyclerView;
    private CuentaAdapter cuentaAdapter;
    private CuentaDao cuentaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cuentas);

        recyclerView = findViewById(R.id.recyclerview_cuentas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener la instancia del DAO de Cuenta
        cuentaDao = AppDatabase.getInstance(getApplicationContext()).cuentaDao();

        // Obtener las cuentas registradas desde la base de datos
        obtenerCuentasRegistradas();
    }

    private void obtenerCuentasRegistradas() {
        // Ejecutar la operación en un hilo de fondo
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                // Obtener las cuentas registradas desde la base de datos
                List<Cuenta> cuentas = cuentaDao.getAllCuentas();

                // Actualizar el adaptador en el hilo principal
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Crear y configurar el adaptador de cuentas
                        cuentaAdapter = new CuentaAdapter(cuentas, ListaCuentasActivity.this);
                        recyclerView.setAdapter(cuentaAdapter);
                    }
                });
            }
        });
    }

    @Override
    public void onCuentaClick(Cuenta cuenta) {
        long cuentaId = cuenta.getId();
        String nombreCuenta = cuenta.getNombre();

        // Abrir la actividad DetalleCuentaActivity y pasar el ID y el nombre de la cuenta seleccionada
        Intent intent = new Intent(ListaCuentasActivity.this, DetalleCuentaActivity.class);
        intent.putExtra("cuenta_id", cuentaId);
        intent.putExtra("nombre_cuenta", nombreCuenta);
        startActivity(intent);
    }
}

