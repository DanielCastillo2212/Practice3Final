package com.example.practica_final.Services;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.practica_final.Entities.Movimiento;

import java.util.List;

@Dao
public interface MovimientoDao {
    @Query("SELECT * FROM movimientos WHERE cuentaId = :cuentaId")
    List<Movimiento> getMovimientosByCuentaId(long cuentaId);


    @Insert
    long insertMovimiento(Movimiento movimiento);
}
