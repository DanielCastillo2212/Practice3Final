package com.example.practica_final.Services;

import com.example.practica_final.Entities.Cuenta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CuentaService {

    @GET("cuenta")
    Call<List<Cuenta>> getCuentas();

    @POST("cuenta")
    Call<Cuenta> createCuenta(@Body Cuenta cuenta);
}