package edu.utleon.idgs702.cuidarte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import edu.utleon.idgs702.cuidarte.dao.UsuarioDAO;
import edu.utleon.idgs702.cuidarte.modelos.Usuario;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private MaterialButton btnLogin;
    private TextView txtSignUpLink; // IMPORTANTE: Añadir esta variable

    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar DAO
        usuarioDAO = new UsuarioDAO(this);

        // Usuario de prueba
        Usuario usuarioPrueba = new Usuario("Juan", "Pérez", "juan", 25, 1234);
        if (!usuarioDAO.usuarioExiste("juan")) {
            usuarioDAO.registrarUsuario(usuarioPrueba);
        }
        
        // Vincular componentes de la UI
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUpLink = findViewById(R.id.txtSignUpLink); // Buscar el TextView "Regístrate"
        
        // Configurar evento del botón de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
        
        // Configurar evento del texto "Regístrate"
        txtSignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir actividad de registro
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void iniciarSesion() {
        // Obtener valores de los campos
        String usuario = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        
        // Validar que los campos no estén vacíos
        if (usuario.isEmpty()) {
            etEmail.setError("Ingresa tu usuario");
            etEmail.requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            etPassword.setError("Ingresa tu contraseña");
            etPassword.requestFocus();
            return;
        }
        
        // Validar que el password sea un número (PIN)
        int pin;
        try {
            pin = Integer.parseInt(password);
        } catch (NumberFormatException e) {
            etPassword.setError("El PIN debe ser numérico");
            etPassword.requestFocus();
            return;
        }
        
        // Intentar iniciar sesión
        Usuario usuarioEncontrado = usuarioDAO.iniciarSesion(usuario, pin);
        
        if (usuarioEncontrado != null) {
            // Login exitoso
            Toast.makeText(this, 
                "¡Bienvenido " + usuarioEncontrado.getNombre() + "!", 
                Toast.LENGTH_SHORT).show();
            
            // Redirigir a la activity menú principal
            Intent intent = new Intent(MainActivity.this, Menu.class);
            intent.putExtra("id_usuario", usuarioEncontrado.getId());
            intent.putExtra("usuario", usuarioEncontrado.getUsuario());
            intent.putExtra("usuario_nombre", usuarioEncontrado.getNombre());
            intent.putExtra("usuario-appellidos", usuarioEncontrado.getApellidos());
            startActivity(intent);
            finish();
            
        } else {
            // Login fallido
            Toast.makeText(this, 
                "Usuario o PIN incorrectos", 
                Toast.LENGTH_SHORT).show();
            etPassword.setText("");
            etPassword.requestFocus();
        }
    }
}