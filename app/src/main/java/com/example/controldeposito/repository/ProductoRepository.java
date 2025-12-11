package com.example.controldeposito.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.controldeposito.model.AppDatabase;
import com.example.controldeposito.model.AppLog;
import com.example.controldeposito.model.Producto;
import com.example.controldeposito.model.ProductoDao;
import com.example.controldeposito.network.RetrofitClient;
import com.example.controldeposito.network.WebhookService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoRepository {

    private ProductoDao productoDao;
    private LiveData<List<Producto>> allProductos;
    private WebhookService apiService;

    public ProductoRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        productoDao = db.productoDao();
        allProductos = productoDao.getAllProductos();
        apiService = RetrofitClient.getService();
    }

    public LiveData<List<Producto>> getAllProductos() {
        return allProductos;
    }

    // Obtener logs para mostrar en pantalla
    public LiveData<List<AppLog>> getAllLogs() {
        return productoDao.getAllLogs();
    }

    // Insertar localmente y luego intentar enviar a la API
    public void insert(Producto producto) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // 1. Insertamos y GUARDAMOS el ID que nos devuelve la BD
            long nuevoId = productoDao.insert(producto);

            // 2. Actualizamos el ID del objeto Java
            producto.id = (int) nuevoId;

            // 3. Logueamos
            logEvent("Producto creado localmente con ID: " + nuevoId, "CREACION");

            // 4. Ahora enviamos a la API el producto que YA TIENE el ID correcto
            enviarAPI(producto);
        });
    }

    // Enviar a Webhook (Retrofit)
    private void enviarAPI(Producto producto) {
        // Log para avisar que empezamos
        Log.d("API_TEST", "Intentando enviar producto: " + producto.nombre);

        Call<Void> call = apiService.enviarProducto(producto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("API_TEST", "¡EXITO! Enviado al Webhook. Código: " + response.code()); // <--- ESTO BUSCAMOS
                    AppDatabase.databaseWriteExecutor.execute(() ->
                            logEvent("Sincronizado con API: " + producto.nombre, "SYNC_OK")
                    );
                } else {
                    Log.e("API_TEST", "FALLO. El servidor respondió error: " + response.code());
                    AppDatabase.databaseWriteExecutor.execute(() ->
                            logEvent("Fallo al sincronizar: " + response.code(), "SYNC_ERROR")
                    );
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_TEST", "ERROR CRÍTICO DE RED: " + t.getMessage()); // <--- SI SALE ESTO, NO HAY INTERNET
                AppDatabase.databaseWriteExecutor.execute(() ->
                        logEvent("Error de red: " + t.getMessage(), "NETWORK_ERROR")
                );
            }
        });
    }

    // Método auxiliar para registrar logs en la BD
    private void logEvent(String mensaje, String tipo) {
        String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        AppLog log = new AppLog(mensaje, tipo, fecha);
        productoDao.insertLog(log);
    }

    public void update(Producto producto) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            productoDao.update(producto);
            logEvent("Producto modificado: " + producto.nombre, "MODIFICACION");
            enviarAPI(producto); // Enviamos el cambio al servidor también
        });
    }

    public void delete(Producto producto) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            productoDao.delete(producto);
            logEvent("Producto eliminado: " + producto.nombre, "ELIMINACION");
        });
    }
}
