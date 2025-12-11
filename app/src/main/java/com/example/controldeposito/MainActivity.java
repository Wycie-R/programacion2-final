package com.example.controldeposito;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.controldeposito.adapter.ProductoAdapter;
import com.example.controldeposito.databinding.ActivityMainBinding;
import com.example.controldeposito.model.Producto;
import com.example.controldeposito.viewmodel.ProductoViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ProductoViewModel viewModel;
    private ProductoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Configurar RecyclerView y Adapter
        adapter = new ProductoAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        // 2. Inicializar ViewModel
        viewModel = new ViewModelProvider(this).get(ProductoViewModel.class);

        // 3. Observar cambios en la BD para actualizar la lista
        viewModel.getAllProductos().observe(this, productos -> {
            adapter.setProductos(productos);
        });

        // 4. Configurar acciones de los botones del Adapter (Editar/Borrar)
        adapter.setOnItemClickListener(new ProductoAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Producto producto) {
                // Abrimos AgregarActivity pero pasándole el producto
                Intent intent = new Intent(MainActivity.this, AgregarActivity.class);
                intent.putExtra("producto_editar", producto);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Producto producto) {
                // Borramos usando el ViewModel
                viewModel.delete(producto);
                Toast.makeText(MainActivity.this, "Producto eliminado: " + producto.nombre, Toast.LENGTH_SHORT).show();
            }
        });

        // 5. Botón Flotante para Agregar (Modo Crear, sin extras)
        binding.fabAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AgregarActivity.class);
            startActivity(intent);
        });

        // 6. Botón para ver Logs
        binding.btnVerLogs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LogsActivity.class);
            startActivity(intent);
        });
    }
}