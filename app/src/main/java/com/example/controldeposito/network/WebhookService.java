package com.example.controldeposito.network;

import com.example.controldeposito.model.Producto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebhookService {
    // Enviamos un producto individual o una lista, depende de lo que prefieras.
    // Para el examen, enviar el objeto que acabamos de crear/editar es suficiente.

    @POST("d2ab37b1-029e-42ae-93b1-5cacfa56686f") // La ruta relativa suele ser vacía en webhook.site porque la URL base ya tiene el ID
    Call<Void> enviarProducto(@Body Producto producto);
}