package com.example.controldeposito.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
import java.io.Serializable;

@Entity(tableName = "productos")
public class Producto implements Serializable{
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre;
    public String descripcion;
    public int cantidad;
    public String imagenPath; // Ruta de la foto tomada
    public String fechaCreacion; // Para cumplir con logs/fechas

    // Constructor vacío para Room
    public Producto() { }

    @Ignore
    public Producto(String nombre, String descripcion, int cantidad, String imagenPath, String fechaCreacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.imagenPath = imagenPath;
        this.fechaCreacion = fechaCreacion;
    }
}