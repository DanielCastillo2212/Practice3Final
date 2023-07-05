package com.example.practica_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.practica_final.Adapters.MovimientoAdapter;
import com.example.practica_final.DB.AppDatabase;
import com.example.practica_final.Entities.Movimiento;
import com.example.practica_final.Services.MovimientoDao;

import java.util.ArrayList;
import java.util.List;

public class ListaMovimientosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovimientoAdapter movimientoAdapter;
    private List<Movimiento> listaMovimientos;
    private MovimientoDao movimientoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_movimientos);

        // Obtén la instancia del DAO de Movimiento
        movimientoDao = AppDatabase.getInstance(getApplicationContext()).movimientoDao();

        // Inicializa la lista de movimientos
        listaMovimientos = new ArrayList<>();

        // Inicializa el RecyclerView
        recyclerView = findViewById(R.id.recycler_view_movimientos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movimientoAdapter = new MovimientoAdapter(listaMovimientos);
        recyclerView.setAdapter(movimientoAdapter);

        // Obtén el cuentaId de los extras del intent
        long cuentaId = getIntent().getLongExtra("cuenta_id", -1);

        // Carga los movimientos desde la base de datos utilizando el cuentaId
        cargarMovimientos(cuentaId);
    }

    private void cargarMovimientos(final long cuentaId) {
        AsyncTask<Void, Void, List<Movimiento>> task = new AsyncTask<Void, Void, List<Movimiento>>() {
            @Override
            protected List<Movimiento> doInBackground(Void... voids) {
                return movimientoDao.getMovimientosByCuentaId(cuentaId);
            }

            @Override
            protected void onPostExecute(List<Movimiento> movimientos) {
                // Actualiza la lista de movimientos con los datos obtenidos de la base de datos
                listaMovimientos.clear();
                listaMovimientos.addAll(movimientos);

                // Notifica al adaptador que los datos han cambiado
                movimientoAdapter.notifyDataSetChanged();
            }
        };

        task.execute();
    }
}

