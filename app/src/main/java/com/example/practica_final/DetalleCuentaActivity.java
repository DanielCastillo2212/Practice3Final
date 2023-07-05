package com.example.practica_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practica_final.DB.AppDatabase;
import com.example.practica_final.Entities.Cuenta;
import com.example.practica_final.Services.CuentaDao;
import com.example.practica_final.Services.CuentaService;
import com.example.practica_final.Services.MovimientoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleCuentaActivity extends AppCompatActivity {
    private TextView tvNombreCuenta;
    private Button btnRegistrarMovimiento;
    private Button btnVerMovimientos;
    private Button btnSincronizar;

    private Retrofit retrofit;
    private CuentaService cuentaService;
    private MovimientoService movimientoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cuenta);

        // Obtener los elementos de la interfaz
        tvNombreCuenta = findViewById(R.id.tv_nombre_cuenta_detalle);
        btnRegistrarMovimiento = findViewById(R.id.btn_registrar_movimiento);
        btnVerMovimientos = findViewById(R.id.btn_ver_movimientos);
        btnSincronizar = findViewById(R.id.btn_sincronizar);

        // Obtener la instancia de Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Obtener el servicio de Cuenta
        cuentaService = retrofit.create(CuentaService.class);

        // Obtener el servicio de Movimiento
        movimientoService = retrofit.create(MovimientoService.class);

        // Simplemente mostraremos el nombre de la cuenta en el TextView de nombre
        String nombreCuenta = getIntent().getStringExtra("nombre_cuenta");
        tvNombreCuenta.setText(nombreCuenta);

        // Obtener el ID de la cuenta seleccionada
        long cuentaId = getIntent().getLongExtra("cuenta_id", -1);

        // Configurar los listeners de los botones
        btnRegistrarMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para registrar un movimiento en la cuenta seleccionada
                // Crea un Intent para abrir la actividad RegistroMovimientoActivity
                Intent intent = new Intent(DetalleCuentaActivity.this, RegistroMovimientoActivity.class);

                // Pasa el cuentaId como extra en el Intent
                intent.putExtra("cuenta_id", cuentaId);

                // Inicia la actividad RegistroMovimientoActivity
                startActivity(intent);

                // Muestra un mensaje o realiza otras acciones si es necesario
                Toast.makeText(DetalleCuentaActivity.this, "Registrar movimiento", Toast.LENGTH_SHORT).show();
            }
        });

        btnVerMovimientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad ListaMovimientosActivity y pasar el cuentaId
                Intent intent = new Intent(DetalleCuentaActivity.this, ListaMovimientosActivity.class);
                intent.putExtra("cuenta_id", cuentaId);
                startActivity(intent);
                Toast.makeText(DetalleCuentaActivity.this, "Ver movimientos", Toast.LENGTH_SHORT).show();
            }
        });

        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para sincronizar la cuenta con el servicio web
                sincronizarCuenta(cuentaId);
            }
        });
    }

    private void sincronizarCuenta(long cuentaId) {
        // Obtener la cuenta de lacuentaId seleccionada de la base de datos local
        Cuenta cuenta = obtenerCuentaLocal(cuentaId);

        // Verificar si la cuenta existe en la base de datos local
        if (cuenta != null) {
            // Hacer la llamada al servicio web para crear la cuenta en MockAPI
            Call<Cuenta> call = cuentaService.createCuenta(cuenta);
            call.enqueue(new Callback<Cuenta>() {
                @Override
                public void onResponse(Call<Cuenta> call, Response<Cuenta> response) {
                    if (response.isSuccessful()) {
                        // La cuenta se sincronizó correctamente
                        Toast.makeText(DetalleCuentaActivity.this, "Cuenta sincronizada exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        // Error al sincronizar la cuenta
                        Toast.makeText(DetalleCuentaActivity.this, "Error al sincronizar la cuenta", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Cuenta> call, Throwable t) {
                    // Error de comunicación con el servidor
                    Toast.makeText(DetalleCuentaActivity.this, "Error de comunicación con el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // La cuenta no existe en la base de datos local
            Toast.makeText(DetalleCuentaActivity.this, "No se encontró la cuenta en la base de datos local", Toast.LENGTH_SHORT).show();
        }
    }

    private Cuenta obtenerCuentaLocal(long cuentaId) {
        // Obtén la instancia de la base de datos
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        // Obtén la cuenta utilizando el cuentaId
        CuentaDao cuentaDao = db.cuentaDao();
        Cuenta cuenta = cuentaDao.getCuentaById(cuentaId);

        // Devuelve la cuenta encontrada o null si no se encuentra
        return cuenta;
    }
}