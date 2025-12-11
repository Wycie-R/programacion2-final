package com.example.controldeposito;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.example.controldeposito.databinding.ActivityAgregarBinding;
import com.example.controldeposito.model.Producto;
import com.example.controldeposito.viewmodel.ProductoViewModel;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AgregarActivity extends AppCompatActivity {

    private ActivityAgregarBinding binding;
    private ProductoViewModel viewModel;
    private String currentPhotoPath;
    private Producto productoEditar = null; // Variable para almacenar el producto si estamos editando
    private static final int PERMISSION_REQUEST_CODE = 100;

    // Lanzador para la cámara
    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Mostrar imagen capturada
                    Glide.with(this).load(currentPhotoPath).into(binding.ivPreview);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgregarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ProductoViewModel.class);

        // --- LÓGICA DE EDICIÓN ---
        // Verificamos si nos enviaron un producto para editar
        if (getIntent().hasExtra("producto_editar")) {
            productoEditar = (Producto) getIntent().getSerializableExtra("producto_editar");

            // Rellenamos los campos con los datos existentes
            if (productoEditar != null) {
                binding.etNombre.setText(productoEditar.nombre);
                binding.etDescripcion.setText(productoEditar.descripcion);
                binding.etCantidad.setText(String.valueOf(productoEditar.cantidad));

                // Recuperamos la ruta de la foto antigua
                currentPhotoPath = productoEditar.imagenPath;

                // Mostramos la foto si existe
                if (currentPhotoPath != null && !currentPhotoPath.isEmpty()) {
                    Glide.with(this).load(currentPhotoPath).into(binding.ivPreview);
                }

                // Cambiamos el texto del botón para que tenga sentido
                binding.btnGuardar.setText("Actualizar Producto");
            }
        }
        // -------------------------

        binding.btnTomarFoto.setOnClickListener(v -> checkCameraPermission());

        binding.btnGuardar.setOnClickListener(v -> guardarProducto());
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        } else {
            abrirCamara();
        }
    }

    private void abrirCamara() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Sin validación resolveActivity para compatibilidad con Android 11+

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Toast.makeText(this, "Error creando archivo", Toast.LENGTH_SHORT).show();
        }

        if (photoFile != null) {
            try {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.controldeposito.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                cameraLauncher.launch(takePictureIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Error cámara: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void guardarProducto() {
        String nombre = binding.etNombre.getText().toString();
        String desc = binding.etDescripcion.getText().toString();
        String cantStr = binding.etCantidad.getText().toString();

        if (nombre.isEmpty() || cantStr.isEmpty()) {
            Toast.makeText(this, "Nombre y cantidad son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidad = Integer.parseInt(cantStr);
        String rutaFoto = (currentPhotoPath != null) ? currentPhotoPath : "";

        if (productoEditar == null) {
            // --- MODO CREAR NUEVO ---
            String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            Producto nuevoProducto = new Producto(nombre, desc, cantidad, rutaFoto, fecha);
            viewModel.insert(nuevoProducto);
            Toast.makeText(this, "Guardado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            // --- MODO ACTUALIZAR (EDITAR) ---
            productoEditar.nombre = nombre;
            productoEditar.descripcion = desc;
            productoEditar.cantidad = cantidad;
            productoEditar.imagenPath = rutaFoto;
            // La fecha de creación la dejamos igual, o puedes actualizarla si prefieres

            viewModel.update(productoEditar);
            Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();
        }

        finish(); // Cierra la pantalla y vuelve a la lista
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamara();
            } else {
                Toast.makeText(this, "Permiso de cámara necesario", Toast.LENGTH_SHORT).show();
            }
        }
    }
}