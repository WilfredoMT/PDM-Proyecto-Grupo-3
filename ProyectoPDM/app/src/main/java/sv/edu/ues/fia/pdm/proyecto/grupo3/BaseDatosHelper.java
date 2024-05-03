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

public class BaseDatosHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DB_NAME = "DBasignaciones";
    private static final int DB_VERSION = 1;

    // Nombres de las tablas
    private static final String LOGIN_TABLA = "login";

    // Tabla Login Columnas //
    static final String KEY_idUsuario = "idUsuario";
    static final String KEY_nombreUsuario = "nombreUsuario";
    static final String KEY_clave = "clave";
    static final String KEY_rol = "rol";
    static final String KEY_imagen_perfil = "imagenPerfil";

                         // Hacer sentencias de creacion SQL //
    //Tabla de login
    private static final String CREATE_LOGIN_TABLA = "CREATE TABLE " + LOGIN_TABLA + " ("
            + KEY_idUsuario + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_nombreUsuario + " TEXT,"
            + KEY_clave + " TEXT,"
            + KEY_rol + " TEXT,"
            + KEY_imagen_perfil + " BLOB)";

    //constructor para el helper
    public BaseDatosHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    // Crear la tabla
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOGIN_TABLA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar tablas si existen
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLA);

        // Crear tablas de nuevo
        onCreate(db);
    }

    //                    Helpers para CRUDs                  //

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