package edu.utleon.idgs702.cuidarte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

public class GraficasDatos extends AppCompatActivity {
    private Button btnCitas;
    private Button btnInicio;
    private Button btnGraficas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_graficas_datos);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

        Intent intent = getIntent();
        int usuarioId = intent.getIntExtra("id_usuario", -1);

        //Menú inferior
        btnCitas = findViewById(R.id.btnCitas);
        btnInicio = findViewById(R.id.btnInicio);
        btnGraficas = findViewById(R.id.btnGraficas);

        btnCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraficasDatos.this, CitasMedicas.class);
                intent.putExtra("id_usuario", usuarioId);
                startActivity(intent);
            }
        });

        // Botón Inicio → No hace nada porque ya estás en esta actividad
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraficasDatos.this, Menu.class);
                intent.putExtra("id_usuario", usuarioId);
                startActivity(intent);
            }
        });

        // Botón Graficas → Abrir actividad GraficasActivity
        btnGraficas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opcional: puedes mostrar un Toast
                Toast.makeText(GraficasDatos.this, "Ya estás en Gráficos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
