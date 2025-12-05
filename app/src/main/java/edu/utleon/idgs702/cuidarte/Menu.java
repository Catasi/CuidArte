package edu.utleon.idgs702.cuidarte;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Button btnCitas;
    private Button btnInicio;
    private Button btnGraficas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        int usuarioId = intent.getIntExtra("id_usuario", -1);

        // Configurar toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurar drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Configurar el toggle del drawer (botón de emergencia)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Seleccionar item
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_home);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Inicio");
            }
        }
        btnCitas = findViewById(R.id.btnCitas);
        btnInicio = findViewById(R.id.btnInicio);
        btnGraficas = findViewById(R.id.btnGraficas);

        VaciarDatosUsuario();

        //Menú inferior

        btnCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, CitasMedicas.class);
                intent.putExtra("id_usuario", usuarioId);
                startActivity(intent);
            }
        });

        // Botón Inicio → No hace nada porque ya estás en esta actividad
            btnInicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Opcional: puedes mostrar un Toast
                    Toast.makeText(Menu.this, "Ya estás en Inicio", Toast.LENGTH_SHORT).show();
                }
            });

        // Botón Graficas → Abrir actividad GraficasActivity
            btnGraficas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Menu.this, GraficasDatos.class);
                    intent.putExtra("id_usuario", usuarioId);
                    startActivity(intent);
                }
            });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Inicio");
            }
            // Aquí puedes cargar el fragmento de inicio

        } else if (id == R.id.nav_chat) {
            Toast.makeText(this, "Chat no disponible", Toast.LENGTH_SHORT).show();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Chat");
            }
            // Aquí puedes cargar el fragmento de chat

        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Configuración no disponible", Toast.LENGTH_SHORT).show();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Configuración");
            }
            // Aquí puedes cargar el fragmento de configuración

        } else if (id == R.id.nav_history) {
            Toast.makeText(this, "Historial no disponible", Toast.LENGTH_SHORT).show();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Historial");
            }
            // Aquí puedes cargar el fragmento de historial

        } else if (id == R.id.nav_notifications) {
            Toast.makeText(this, "Notificaciones no disponibles", Toast.LENGTH_SHORT).show();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Notificaciones");
            }
            // Aquí puedes cargar el fragmento de notificaciones

        } else if (id == R.id.nav_profile) {
            Toast.makeText(this, "Perfil PENDIENTE", Toast.LENGTH_SHORT).show();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Perfil");
            }
            // Aquí puedes cargar el fragmento de perfil

        } else if (id == R.id.nav_help) {
            Toast.makeText(this, "Ayuda aún no disponible", Toast.LENGTH_SHORT).show();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Ayuda");
            }
            // Aquí puedes cargar el fragmento de ayuda

        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show();
            cerrarSesion();
        }

        // Cerrar el drawer después de seleccionar un item
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void VaciarDatosUsuario(){
        //Obtener datos el Usuario
        Intent intent = getIntent();
        int usuarioId = intent.getIntExtra("usuario_id", -1); // -1 es valor por defecto si no existe
        String usuarioUsuario = intent.getStringExtra("usuario");
        String usuarioNombre = intent.getStringExtra("usuario_nombre");
        String usuarioApellidos = intent.getStringExtra("usuario-appellidos");

        //Vaciar los datos del Usuario
        View headerView = navigationView.getHeaderView(0);
        TextView txtUser = headerView.findViewById(R.id.txtUser);
        TextView txtUserName = headerView.findViewById(R.id.txtUserName);
        // Insertar los datos
        txtUser.setText(usuarioUsuario);
        txtUserName.setText(usuarioNombre + " " + usuarioApellidos);
    }

    private void cerrarSesion() {
//         Aquí implementa tu lógica de cierre de sesión
//         Por ejemplo:
         SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
         preferences.edit().clear().apply();
         Intent intent = new Intent(this, MainActivity.class);
         startActivity(intent);
         finish();
    }

    @Override
    public void onBackPressed() {
        // Si el drawer está abierto, cerrarlo al presionar back
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}