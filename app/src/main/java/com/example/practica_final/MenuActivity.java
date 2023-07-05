package com.example.practica_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button btnRegistrarCuentaMenu;
    private Button btnVerCuentasMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnRegistrarCuentaMenu = findViewById(R.id.btn_registrar_cuenta_menu);
        btnVerCuentasMenu = findViewById(R.id.btn_ver_cuentas_menu);

        btnRegistrarCuentaMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a la actividad de registro de cuenta
                Intent intent = new Intent(MenuActivity.this, RegistroCuentaActivity.class);
                startActivity(intent);
            }
        });

        btnVerCuentasMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a la actividad de ver cuentas
                Intent intent = new Intent(MenuActivity.this, ListaCuentasActivity.class);
                startActivity(intent);
            }
        });
    }
}