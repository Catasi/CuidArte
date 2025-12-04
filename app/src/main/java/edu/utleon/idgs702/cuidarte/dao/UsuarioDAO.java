package edu.utleon.idgs702.cuidarte.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.utleon.idgs702.cuidarte.MainActivity;
import edu.utleon.idgs702.cuidarte.database.DataBaseHelper;
import edu.utleon.idgs702.cuidarte.modelos.Usuario;

public class UsuarioDAO {
    private DataBaseHelper dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    // Registrar nuevo usuario
    public long registrarUsuario(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", usuario.getNombre());
        values.put("apellidos", usuario.getApellidos());
        values.put("usuario", usuario.getUsuario());
        values.put("edad", usuario.getEdad());
        values.put("pin", usuario.getPin());

        long id = db.insert("usuario", null, values);
        db.close();
        return id;
    }

    // Iniciar sesión (verificar usuario y pin)
    public Usuario iniciarSesion(String usuario, int pin) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Usuario usuarioEncontrado = null;

        Cursor cursor = db.query(
                "usuario",
                null,
                "usuario=? AND pin=?",
                new String[]{usuario, String.valueOf(pin)},
                null, null, null
        );

        if (cursor.moveToFirst()) {
            usuarioEncontrado = new Usuario();
            usuarioEncontrado.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            usuarioEncontrado.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            usuarioEncontrado.setApellidos(cursor.getString(cursor.getColumnIndexOrThrow("apellidos")));
            usuarioEncontrado.setUsuario(cursor.getString(cursor.getColumnIndexOrThrow("usuario")));
            usuarioEncontrado.setEdad(cursor.getInt(cursor.getColumnIndexOrThrow("edad")));
            usuarioEncontrado.setPin(cursor.getInt(cursor.getColumnIndexOrThrow("pin")));
        }

        cursor.close();
        db.close();
        return usuarioEncontrado;
    }

    // Método adicional: verificar si un nombre de usuario ya existe
    public boolean usuarioExiste(String usuario) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                "usuario",
                new String[]{"id"},
                "usuario=?",
                new String[]{usuario},
                null, null, null
        );

        boolean existe = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return existe;
    }
}
