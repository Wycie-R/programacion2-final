package com.example.controldeposito;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.controldeposito.viewmodel.ProductoViewModel;

public class LogsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        TextView tvLogs = findViewById(R.id.tvLogsContent);
        ProductoViewModel viewModel = new ViewModelProvider(this).get(ProductoViewModel.class);

        // Observar Logs
        viewModel.getAllLogs().observe(this, logs -> {
            StringBuilder sb = new StringBuilder();
            if(logs != null){
                for (com.example.controldeposito.model.AppLog log : logs) {
                    sb.append("[").append(log.fechaHora).append("] ")
                            .append(log.tipo).append(": ")
                            .append(log.mensaje).append("\n\n");
                }
                tvLogs.setText(sb.toString());
            } else {
                tvLogs.setText("No hay logs registrados.");
            }
        });
    }
}