package com.example.controldeposito.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ProductoDao {
    @Insert
    long insert(Producto producto);

    @Update
    void update(Producto producto);

    @Delete
    void delete(Producto producto);

    @Query("SELECT * FROM productos ORDER BY id DESC")
    LiveData<List<Producto>> getAllProductos();

    // Método para obtener lista simple (no LiveData) para enviar por Retrofit
    @Query("SELECT * FROM productos")
    List<Producto> getProductosList();

    // --- Parte de Logs ---
    @Insert
    void insertLog(AppLog log);

    @Query("SELECT * FROM app_logs ORDER BY id DESC")
    LiveData<List<AppLog>> getAllLogs();
}