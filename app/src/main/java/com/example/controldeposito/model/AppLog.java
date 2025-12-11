package com.example.controldeposito.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "app_logs")
public class AppLog {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String mensaje;
    public String tipo; // Ej: "CREACION", "ERROR", "SYNC"
    public String fechaHora;

    public AppLog(String mensaje, String tipo, String fechaHora) {
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.fechaHora = fechaHora;
    }
}