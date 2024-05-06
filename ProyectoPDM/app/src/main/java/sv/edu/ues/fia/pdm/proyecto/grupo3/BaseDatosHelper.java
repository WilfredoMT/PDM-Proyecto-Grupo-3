package sv.edu.ues.fia.pdm.proyecto.grupo3;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.Calendar;

public class BaseDatosHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DB_NAME = "DBasignaciones";
    private static final int DB_VERSION = 1;

    // Nombres de las tablas
    private static final String LOGIN_TABLA = "login";
    public static final String CICLO_TABLA = "ciclo";


    // Tabla Login Columnas //
    static final String KEY_idUsuario = "idUsuario";
    static final String KEY_nombreUsuario = "nombreUsuario";
    static final String KEY_clave = "clave";
    static final String KEY_rol = "rol";
    static final String KEY_imagen_perfil = "imagenPerfil";

    // Tabla Ciclo Columnas

    public static final String KEY_idCiclo = "idCiclo";
    public static final String KEY_nombreCiclo = "nombre";
    public static final String KEY_fechaInicioCiclo = "fechaInicio";
    public static final String KEY_fechaFinCiclo = "fechaFin";


                         // Hacer sentencias de creacion SQL //
    //Tabla de login
    private static final String CREATE_LOGIN_TABLA = "CREATE TABLE " + LOGIN_TABLA + " ("
            + KEY_idUsuario + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_nombreUsuario + " TEXT,"
            + KEY_clave + " TEXT,"
            + KEY_rol + " TEXT,"
            + KEY_imagen_perfil + " BLOB)";

    //Tabla de login
    private static final String CREATE_CICLO_TABLA = "CREATE TABLE " + CICLO_TABLA + " ("
            + KEY_idCiclo + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_nombreCiclo + " TEXT,"
            + KEY_fechaInicioCiclo + " DATE,"
            + KEY_fechaFinCiclo + " DATE)";

    //constructor para el helper
    public BaseDatosHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    // Crear las tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOGIN_TABLA);
        db.execSQL(CREATE_CICLO_TABLA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar tablas si existen
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + CICLO_TABLA);

        // Crear tablas de nuevo
        onCreate(db);
    }

    //                    Helpers para CRUDs                  //

                            //Tabla ciclo//

    //agregar ciclo
    public boolean agregarCiclo(String nombre, String fechaInicio, String fechaFin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreCiclo, nombre);
        values.put(KEY_fechaInicioCiclo, fechaInicio);
        values.put(KEY_fechaFinCiclo, fechaFin);
        long result = db.insert(CICLO_TABLA, null, values);
        return result != -1;
    }

    //Get Ciclo
    public Cursor getCiclo(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CICLO_TABLA, new String[]{KEY_idCiclo,
                        KEY_nombreCiclo, KEY_fechaInicioCiclo, KEY_fechaFinCiclo}, KEY_idCiclo + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar ciclo
    public boolean actualizarCiclo(String idCiclo, String nombre, String fechaInicio, String fechaFin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreCiclo, nombre);
        values.put(KEY_fechaInicioCiclo, fechaInicio);
        values.put(KEY_fechaFinCiclo, fechaFin);
        int rowsAffected = db.update(CICLO_TABLA, values, KEY_idCiclo + " = ?", new String[]{idCiclo});
        return rowsAffected > 0;
    }

    //Eliminar Ciclo
    public boolean eliminarCiclo(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(CICLO_TABLA, KEY_idCiclo + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    //Ver si ya existe Ciclo registrado
    public boolean existeCiclo(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Query para ver si ya existe el mismo nombre
            String query = "SELECT * FROM " + CICLO_TABLA + " WHERE " + KEY_nombreCiclo + "=?";
            cursor = db.rawQuery(query, new String[]{nombre});

            // si cursor tiene al menos una row, ciclo ya existe
            return cursor.getCount() > 0;
        } finally {
            // cerrar cursor y db
            if (cursor != null)
                cursor.close();
            db.close();
        }
    }


    //Insertar Datos iniciales
    public void insertarDatosInicialesCiclo() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + CICLO_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            agregarCiclo("Ciclo 01 2023", "2023-03-05", "2023-07-10");
            agregarCiclo("Ciclo 02 2023", "2023-07-20", "2023-12-10");
            agregarCiclo("Ciclo 01 2024", "2024-03-05", "2024-07-10");
            agregarCiclo("Ciclo 02 2024", "2024-07-20", "2024-12-10");
        }
    }






                            //Tabla Login//
    //Añadir usuario
    public boolean agregarUsuario(String nombreusuario, String clave, String rol, Bitmap imagenpefil) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreUsuario, nombreusuario);
        values.put(KEY_clave, clave);
        values.put(KEY_rol, rol);
        values.put(KEY_imagen_perfil, getBytesFromBitmap(imagenpefil));

        long result = db.insert(LOGIN_TABLA, null, values);
        return result != -1;
    }

    //Bitmap a vector de bytes
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    //Vector de bytes a Bitmap
    public Bitmap getBitmapFromBytes(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    //Get Usuario
    public Cursor getUsuario(String nombreUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(LOGIN_TABLA, new String[]{KEY_idUsuario,
                        KEY_nombreUsuario, KEY_clave, KEY_imagen_perfil, KEY_rol}, KEY_nombreUsuario + "=?",
                new String[]{nombreUsuario}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar Usuario
    public boolean actualizarUsuario(String nombreusuario, String clave, String rol) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_clave, clave);
        values.put(KEY_rol, rol);
        int rowsAffected = db.update(LOGIN_TABLA, values, KEY_nombreUsuario + " = ?", new String[]{nombreusuario});
        return rowsAffected > 0;
    }

    //Eliminar Usuario
    public boolean eliminarUsuario(String nombreusuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(LOGIN_TABLA, KEY_nombreUsuario + " = ?", new String[]{nombreusuario});
        return rowsAffected > 0;
    }

    //Insertar Datos iniciales
    public void insertarDatosInicialesUsuarios() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + LOGIN_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            agregarUsuario("UsuarioAdmin", "PDM115", "Administrador", BitmapFactory.decodeResource(context.getResources(), R.drawable.admin));
            agregarUsuario("UsuarioCoordinador", "PDM115", "Coordinador", BitmapFactory.decodeResource(context.getResources(), R.drawable.coordinacion));
            agregarUsuario("UsuarioDocente", "PDM115", "Docente", BitmapFactory.decodeResource(context.getResources(), R.drawable.docente));
        }
    }














}