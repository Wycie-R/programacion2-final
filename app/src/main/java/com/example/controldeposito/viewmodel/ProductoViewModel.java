package com.example.controldeposito.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.controldeposito.model.AppLog;
import com.example.controldeposito.model.Producto;
import com.example.controldeposito.repository.ProductoRepository;

import java.util.List;

public class ProductoViewModel extends AndroidViewModel {

    private ProductoRepository repository;
    private LiveData<List<Producto>> allProductos;
    private LiveData<List<AppLog>> allLogs;

    public ProductoViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductoRepository(application);
        allProductos = repository.getAllProductos();
        allLogs = repository.getAllLogs();
    }

    public LiveData<List<Producto>> getAllProductos() {
        return allProductos;
    }

    public LiveData<List<AppLog>> getAllLogs() {
        return allLogs;
    }

    public void insert(Producto producto) {
        repository.insert(producto);
    }

    public void update(Producto producto) {
        repository.update(producto); }
    public void delete(Producto producto) {
        repository.delete(producto); }
}