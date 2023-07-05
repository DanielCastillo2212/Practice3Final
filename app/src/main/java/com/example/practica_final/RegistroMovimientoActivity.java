package com.example.practica_final;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practica_final.DB.AppDatabase;
import com.example.practica_final.Entities.Cuenta;
import com.example.practica_final.Entities.Movimiento;
import com.example.practica_final.Services.MovimientoDao;

public class RegistroMovimientoActivity extends AppCompatActivity {

    private RadioGroup rgTipoMovimiento;
    private TextView tvTipoMovimiento;
    private EditText etCantidad;
    private EditText etMotivo;
    private EditText etLatitud;
    private EditText etLongitud;
    private EditText etUrlImagen;
    private Button btnRegistrarMovimiento;

    private long cuentaSeleccionada;
    private MovimientoDao movimientoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_movimiento);

        // Obtener la cuenta seleccionada (puedes obtenerla a través de Intent o de cualquier otra forma)
        cuentaSeleccionada = getIntent().getLongExtra("cuenta_id", -1);

        // Obtener la instancia del DAO de Movimiento
        movimientoDao = AppDatabase.getInstance(getApplicationContext()).movimientoDao();

        // Obtener los elementos de la interfaz
        rgTipoMovimiento = findViewById(R.id.rg_tipo_movimiento);
        tvTipoMovimiento = findViewById(R.id.tv_tipo_movimiento);
        etCantidad = findViewById(R.id.et_cantidad);
        etMotivo = findViewById(R.id.et_motivo);
        etLatitud = findViewById(R.id.et_latitud);
        etLongitud = findViewById(R.id.et_longitud);
        etUrlImagen = findViewById(R.id.et_url_imagen);
        btnRegistrarMovimiento = findViewById(R.id.btn_guardar_movimiento);

        // Configurar listener para capturar el tipo de movimiento seleccionado
        rgTipoMovimiento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_ingreso) {
                    tvTipoMovimiento.setText("Tipo de Movimiento: Ingreso");
                } else if (checkedId == R.id.rb_gasto) {
                    tvTipoMovimiento.setText("Tipo de Movimiento: Gasto");
                }
            }
        });

        // Configurar el listener del botón de registro
        btnRegistrarMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados en los campos de texto
                String tipoMovimiento = tvTipoMovimiento.getText().toString().trim();
                String cantidadString = etCantidad.getText().toString().trim();
                String motivo = etMotivo.getText().toString().trim();
                String latitudString = etLatitud.getText().toString().trim();
                String longitudString = etLongitud.getText().toString().trim();
                String urlImagen = etUrlImagen.getText().toString().trim();

                // Validar los datos ingresados
                if (!tipoMovimiento.isEmpty() && !cantidadString.isEmpty() && !motivo.isEmpty()
                        && !latitudString.isEmpty() && !longitudString.isEmpty() && !urlImagen.isEmpty()) {
                    double cantidad = Double.parseDouble(cantidadString);

                    // Crear un nuevo objeto Movimiento con los datos ingresados
                    final Movimiento nuevoMovimiento = new Movimiento(
                            cuentaSeleccionada,
                            tipoMovimiento,
                            cantidad,
                            motivo,
                            latitudString,
                            longitudString,
                            urlImagen
                    );

                    AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            // Insertar el movimiento en la base de datos de movimientos
                            movimientoDao.insertMovimiento(nuevoMovimiento);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            // Mostrar mensaje de éxito
                            Toast.makeText(RegistroMovimientoActivity.this, "Movimiento registrado exitosamente", Toast.LENGTH_SHORT).show();

                            // Limpiar los campos de texto
                            etCantidad.setText("");
                            etMotivo.setText("");
                            etLatitud.setText("");
                            etLongitud.setText("");
                            etUrlImagen.setText("");
                        }
                    };

                    task.execute();
                } else {
                    // Mostrar mensaje de error si no se completaron todos los campos
                    Toast.makeText(RegistroMovimientoActivity.this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
