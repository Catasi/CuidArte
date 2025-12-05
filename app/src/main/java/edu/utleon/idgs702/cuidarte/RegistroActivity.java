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

public class RegistroActivity extends AppCompatActivity {
    
    private TextInputEditText etNombre, etApellidos, etUsuario, etEdad, etPin, etConfirmarPin;
    private MaterialButton btnRegistrar;
    private TextView txtLoginLink;
    private UsuarioDAO usuarioDAO;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        
        // Inicializar DAO
        usuarioDAO = new UsuarioDAO(this);
        
        // Vincular componentes de la UI
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etUsuario = findViewById(R.id.etUsuario);
        etEdad = findViewById(R.id.etEdad);
        etPin = findViewById(R.id.etPin);
        etConfirmarPin = findViewById(R.id.etConfirmarPin);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        txtLoginLink = findViewById(R.id.txtLoginLink);
        
        // Configurar evento del botón de registro
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
        
        // Configurar evento del texto "Inicia Sesión"
        txtLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar al login
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    
    private void registrarUsuario() {
        // Obtener valores de los campos
        String nombre = etNombre.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String usuario = etUsuario.getText().toString().trim();
        String strEdad = etEdad.getText().toString().trim();
        String strPin = etPin.getText().toString().trim();
        String strConfirmarPin = etConfirmarPin.getText().toString().trim();
        
        // Validar campos
        if (!validarCampos(nombre, apellidos, usuario, strEdad, strPin, strConfirmarPin)) {
            return;
        }
        
        // Convertir valores
        int edad = Integer.parseInt(strEdad);
        int pin = Integer.parseInt(strPin);
        
        // Verificar si usuario ya existe
        if (usuarioDAO.usuarioExiste(usuario)) {
            etUsuario.setError("Este usuario ya está registrado");
            etUsuario.requestFocus();
            return;
        }
        
        // Crear objeto Usuario
        Usuario nuevoUsuario = new Usuario(nombre, apellidos, usuario, edad, pin);
        
        // Registrar en base de datos
        long id = usuarioDAO.registrarUsuario(nuevoUsuario);
        
        if (id != -1) {
            // Registro exitoso
            Toast.makeText(this, 
                "¡Registro exitoso! Ya puedes iniciar sesión", 
                Toast.LENGTH_SHORT).show();
            
            // Redirigir al login
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            
        } else {
            // Error en el registro
            Toast.makeText(this, 
                "Error al registrar usuario", 
                Toast.LENGTH_SHORT).show();
        }
    }
    
    private boolean validarCampos(String nombre, String apellidos, String usuario, 
                                  String strEdad, String strPin, String strConfirmarPin) {
        
        // Validar nombre
        if (nombre.isEmpty()) {
            etNombre.setError("El nombre es requerido");
            etNombre.requestFocus();
            return false;
        }
        
        // Validar apellidos
        if (apellidos.isEmpty()) {
            etApellidos.setError("Los apellidos son requeridos");
            etApellidos.requestFocus();
            return false;
        }
        
        // Validar usuario
        if (usuario.isEmpty()) {
            etUsuario.setError("El usuario es requerido");
            etUsuario.requestFocus();
            return false;
        }
        
        if (usuario.length() < 4) {
            etUsuario.setError("El usuario debe tener al menos 4 caracteres");
            etUsuario.requestFocus();
            return false;
        }
        
        // Validar edad
        if (strEdad.isEmpty()) {
            etEdad.setError("La edad es requerida");
            etEdad.requestFocus();
            return false;
        }
        
        int edad;
        try {
            edad = Integer.parseInt(strEdad);
        } catch (NumberFormatException e) {
            etEdad.setError("La edad debe ser un número");
            etEdad.requestFocus();
            return false;
        }
        
        if (edad < 1 || edad > 120) {
            etEdad.setError("Ingrese una edad válida (1-120)");
            etEdad.requestFocus();
            return false;
        }
        
        // Validar PIN
        if (strPin.isEmpty()) {
            etPin.setError("El PIN es requerido");
            etPin.requestFocus();
            return false;
        }
        
        if (strPin.length() != 4) {
            etPin.setError("El PIN debe tener 4 dígitos");
            etPin.requestFocus();
            return false;
        }
        
        try {
            Integer.parseInt(strPin);
        } catch (NumberFormatException e) {
            etPin.setError("El PIN debe contener solo números");
            etPin.requestFocus();
            return false;
        }
        
        // Validar confirmación de PIN
        if (strConfirmarPin.isEmpty()) {
            etConfirmarPin.setError("Confirme su PIN");
            etConfirmarPin.requestFocus();
            return false;
        }
        
        if (!strPin.equals(strConfirmarPin)) {
            etConfirmarPin.setError("Los PIN no coinciden");
            etConfirmarPin.requestFocus();
            return false;
        }
        
        return true;
    }
}