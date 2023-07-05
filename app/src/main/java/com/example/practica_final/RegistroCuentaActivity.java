package com.example.practica_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practica_final.DB.AppDatabase;
import com.example.practica_final.Entities.Cuenta;
import com.example.practica_final.Services.CuentaDao;

import java.util.concurrent.Executors;

public class RegistroCuentaActivity extends AppCompatActivity {
    private EditText etNombreCuenta;
    private Button btnRegistrarCuenta;
    private CuentaDao cuentaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cuenta);

        etNombreCuenta = findViewById(R.id.et_nombre_cuenta);
        btnRegistrarCuenta = findViewById(R.id.btn_registrar_cuenta);

        // Obtener la instancia del DAO de Cuenta
        cuentaDao = AppDatabase.getInstance(getApplicationContext()).cuentaDao();

        btnRegistrarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreCuenta = etNombreCuenta.getText().toString().trim();

                if (!nombreCuenta.isEmpty()) {
                    // Crear una instancia de Cuenta con el nombre proporcionado
                    Cuenta cuenta = new Cuenta(nombreCuenta, 0);

                    // Guardar la cuenta en la base de datos
                    guardarCuenta(cuenta);

                    // Mostrar mensaje de éxito
                    Toast.makeText(RegistroCuentaActivity.this, "Cuenta registrada exitosamente", Toast.LENGTH_SHORT).show();

                    // Restablecer el campo de texto
                    etNombreCuenta.setText("");
                } else {
                    // Mostrar mensaje de error si no se ingresó un nombre de cuenta válido
                    Toast.makeText(RegistroCuentaActivity.this, "Ingrese un nombre de cuenta válido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void guardarCuenta(final Cuenta cuenta) {
        // Ejecutar la operación en un hilo de fondo
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                // Insertar la cuenta en la base de datos
                cuentaDao.insertCuenta(cuenta);
            }
        });
    }
}
