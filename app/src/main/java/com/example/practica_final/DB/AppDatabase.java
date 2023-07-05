package com.example.practica_final.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.practica_final.Entities.Cuenta;
import com.example.practica_final.Entities.Movimiento;
import com.example.practica_final.Services.CuentaDao;
import com.example.practica_final.Services.MovimientoDao;

@Database(entities = {Cuenta.class, Movimiento.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract CuentaDao cuentaDao();
    public abstract MovimientoDao movimientoDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
