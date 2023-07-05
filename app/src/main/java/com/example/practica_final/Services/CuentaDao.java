package com.example.practica_final.Services;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.practica_final.Entities.Cuenta;

import java.util.List;

@Dao
public interface CuentaDao {
    @Query("SELECT * FROM cuentas")
    List<Cuenta> getAllCuentas();

    @Insert
    long insertCuenta(Cuenta cuenta);
}