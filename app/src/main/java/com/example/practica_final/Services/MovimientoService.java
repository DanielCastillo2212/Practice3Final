package com.example.practica_final.Services;

import com.example.practica_final.Entities.Movimiento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MovimientoService {

    @GET("cuentas/{cuentaId}/movimientos")
    Call<List<Movimiento>> getMovimientosByCuentaId(@Path("cuentaId") long cuentaId);

    @POST("movimientos")
    Call<Movimiento> createMovimiento(@Body Movimiento movimiento);
}
