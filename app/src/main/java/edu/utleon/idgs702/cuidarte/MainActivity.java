package edu.utleon.idgs702.cuidarte;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import edu.utleon.idgs702.cuidarte.dao.UsuarioDAO;
import edu.utleon.idgs702.cuidarte.models.Usuario;


public class MainActivity extends AppCompatActivity{
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private MaterialButton btnLogin;

    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState ){
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
        
        // Configurar evento del botón de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
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
            // Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            // intent.putExtra("usuario_id", usuarioEncontrado.getId());
            // intent.putExtra("usuario_nombre", usuarioEncontrado.getNombre());
            // startActivity(intent);
            // finish();
            
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
