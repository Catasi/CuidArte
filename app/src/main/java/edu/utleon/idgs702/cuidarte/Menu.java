package edu.utleon.idgs702.cuidarte;

import android.os.Bundle;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Configurar toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurar drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Configurar el toggle del drawer (botón de hamburguesa)
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
            Toast.makeText(this, "Chat", Toast.LENGTH_SHORT).show();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Chat");
            }
            // Aquí puedes cargar el fragmento de chat

        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Configuración", Toast.LENGTH_SHORT).show();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Configuración");
            }
            // Aquí puedes cargar el fragmento de configuración

        } else if (id == R.id.nav_history) {
            Toast.makeText(this, "Historial", Toast.LENGTH_SHORT).show();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Historial");
            }
            // Aquí puedes cargar el fragmento de historial

        } else if (id == R.id.nav_notifications) {
            Toast.makeText(this, "Notificaciones", Toast.LENGTH_SHORT).show();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Notificaciones");
            }
            // Aquí puedes cargar el fragmento de notificaciones

        } else if (id == R.id.nav_profile) {
            Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Perfil");
            }
            // Aquí puedes cargar el fragmento de perfil

        } else if (id == R.id.nav_help) {
            Toast.makeText(this, "Ayuda", Toast.LENGTH_SHORT).show();
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

    private void cerrarSesion() {
        // Aquí implementa tu lógica de cierre de sesión
        // Por ejemplo:
        // SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        // preferences.edit().clear().apply();
        // Intent intent = new Intent(this, LoginActivity.class);
        // startActivity(intent);
        // finish();
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