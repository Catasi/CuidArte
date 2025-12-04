package edu.utleon.idgs702.cuidarte;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cuidarte.db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Aquí puedes crear las tablas necesarias
        String CREATE_USUARIO_TABLE = "CREATE TABLE usuario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "apellidos TEXT," +
                "usuario TEXT," +
                "edad INTEGER," +
                "pin INTEGER" +
                ")";
        db.execSQL(CREATE_USUARIO_TABLE);

        String CREATE_CITA_TABLE = "CREATE TABLE cita (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idUsuario INTEGER," +
                "fecha TEXT," +
                "hora TEXT," +
                "lugar TEXT," +
                "doctor TEXT," +
                "especialista TEXT," +
                "FOREIGN KEY(idUsuario) REFERENCES usuario(id)" +
                ")";
        db.execSQL(CREATE_CITA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes manejar las actualizaciones de la base de datos
        db.execSQL("DROP TABLE IF EXISTS cita");
        db.execSQL("DROP TABLE IF EXISTS usuario");
        onCreate(db);
    }

}
