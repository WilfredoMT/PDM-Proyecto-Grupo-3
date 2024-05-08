package sv.edu.ues.fia.pdm.proyecto.grupo3;
import android.annotation.SuppressLint;
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
    public static final String ASIGNATURA_TABLA = "asignatura";
    public static final String LOCAL_TABLA = "local";


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

                  //Tabla asigaturas columnas
    public static final String KEY_idAsignatura = "idAsignatura";
    public static final String KEY_nombreAsignatura = "nombre";
    public static final String KEY_codigoAsignatura = "codigo";
    public static final String KEY_numeroCicloAsignatura = "numeroCiclo";

    //Tabla Locales columna
    public static final String KEY_idLocal = "idLocal";
    public static final String KEY_nombreLocal = "nombre";
    public static final String KEY_tipoLocal = "tipo";
    public static final String KEY_capacidadLocal = "capacidad";




                         // Hacer sentencias de creacion SQL //


    //Tabla de locales
    private static final String CREATE_LOCAL_TABLA = "CREATE TABLE " + LOCAL_TABLA + " ("
            + KEY_idLocal + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_nombreLocal + " TEXT,"
            + KEY_tipoLocal + " TEXT,"
            + KEY_capacidadLocal + " INTEGER)";

    //Tabla de asignaturas
    private static final String CREATE_ASIGNATURA_TABLA = "CREATE TABLE " + ASIGNATURA_TABLA + " ("
            + KEY_idAsignatura + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_nombreAsignatura + " TEXT,"
            + KEY_numeroCicloAsignatura + " INTEGER,"
            + KEY_codigoAsignatura + " TEXT)";

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
        db.execSQL(CREATE_ASIGNATURA_TABLA);
        db.execSQL(CREATE_LOCAL_TABLA);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar tablas si existen
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + CICLO_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + ASIGNATURA_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + LOCAL_TABLA);

        // Crear tablas de nuevo
        onCreate(db);
    }

    //                    Helpers para CRUDs                  //

                        //Tabla locales//

    //agregar local
    public boolean agregarLocal(String nombre, String tipo, String capacidad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreLocal, nombre);
        values.put(KEY_tipoLocal, tipo);
        values.put(KEY_capacidadLocal, capacidad);
        long result = db.insert(LOCAL_TABLA, null, values);
        return result != -1;
    }

    //Get Local
    public Cursor getLocal(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(LOCAL_TABLA, new String[]{KEY_idLocal,
                        KEY_nombreLocal, KEY_tipoLocal, KEY_capacidadLocal}, KEY_idAsignatura + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar local
    public boolean actualizarLocal(String idLocal, String nombre, String tipo, String capacidad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreLocal, nombre);
        values.put(KEY_tipoLocal, tipo);
        values.put(KEY_capacidadLocal, capacidad);
        int rowsAffected = db.update(LOCAL_TABLA, values, KEY_idLocal + " = ?", new String[]{idLocal});
        return rowsAffected > 0;
    }

    //Eliminar local
    public boolean eliminarLocal(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(LOCAL_TABLA, KEY_idLocal + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    //Ver si ya existe local registrado
    public boolean existeLocal(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Query para ver si ya existe el mismo codigo
            String query = "SELECT * FROM " + LOCAL_TABLA + " WHERE " + KEY_nombreLocal + "=?";
            cursor = db.rawQuery(query, new String[]{nombre});
            // si cursor tiene al menos una row, asignatura ya existe
            return cursor.getCount() > 0;
        } finally {
            // cerrar cursor y db
            if (cursor != null)
                cursor.close();
            db.close();
        }
    }
    //Sobrecargar metodo
    public boolean existeLocal(String nombre, String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        // Query para ver si ya existe el mismo codigo
        String query = "SELECT * FROM " + LOCAL_TABLA + " WHERE " + KEY_nombreLocal + "=?";
        cursor = db.rawQuery(query, new String[]{nombre});
        // si cursor tiene al menos una row, asignatura ya existe
        boolean hayRow= cursor.getCount() > 0;

        if(hayRow == true) {
            cursor.moveToFirst();
            @SuppressLint("Range") int idEncontrado = cursor.getInt(cursor.getColumnIndex(KEY_idLocal));
            if (idEncontrado == Integer.parseInt(id))
            {
                return false;
            }
            else {
                cursor.close();
                db.close();
                return true;
            }
        }

        return true;
    }



    //Insertar Datos iniciales de local
    public void insertarDatosInicialesLocal() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + LOCAL_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            agregarLocal("Miguel Marmol", "Auditorio", "100");
            agregarLocal("Infocentros", "Salon", "25");
            agregarLocal("Edificio B ", "Edificio", "120");
            agregarLocal("Edificio C", "Edificio", "120");

        }
    }


    //Tabla asignatura//

    //agregar asignaturas
    public boolean agregarAsignaturas(String nombre, String codigo, String numeroCiclo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreAsignatura, nombre);
        values.put(KEY_codigoAsignatura, codigo);
        values.put(KEY_numeroCicloAsignatura, numeroCiclo);
        long result = db.insert(ASIGNATURA_TABLA, null, values);
        return result != -1;
    }

    //Get Asignatura
    public Cursor getAsignatura(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ASIGNATURA_TABLA, new String[]{KEY_idAsignatura,
                        KEY_nombreAsignatura, KEY_codigoAsignatura, KEY_numeroCicloAsignatura}, KEY_idAsignatura + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar asignaturas
    public boolean actualizarAsignatura(String idAsignatura, String nombre, String codigo, String numeroCicloAsignatura) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreAsignatura, nombre);
        values.put(KEY_codigoAsignatura, codigo);
        values.put(KEY_numeroCicloAsignatura, numeroCicloAsignatura);
        int rowsAffected = db.update(ASIGNATURA_TABLA, values, KEY_idAsignatura + " = ?", new String[]{idAsignatura});
        return rowsAffected > 0;
    }

    //Eliminar Asignatura
    public boolean eliminarAsignatura(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(ASIGNATURA_TABLA, KEY_idAsignatura + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    //Ver si ya existe asignatura registrado
    public boolean existeAsignatura(String codigo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Query para ver si ya existe el mismo codigo
            String query = "SELECT * FROM " + ASIGNATURA_TABLA + " WHERE " + KEY_codigoAsignatura + "=?";
            cursor = db.rawQuery(query, new String[]{codigo});
            // si cursor tiene al menos una row, asignatura ya existe
            return cursor.getCount() > 0;
        } finally {
            // cerrar cursor y db
            if (cursor != null)
                cursor.close();
            db.close();
        }
    }
    //Sobrecargar metodo
    public boolean existeAsignatura(String nombre, String codigo, String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

            // Query para ver si ya existe el mismo codigo
            String query = "SELECT * FROM " + ASIGNATURA_TABLA + " WHERE " + KEY_codigoAsignatura + "=?";
            cursor = db.rawQuery(query, new String[]{codigo});
            // si cursor tiene al menos una row, asignatura ya existe
            boolean hayRow= cursor.getCount() > 0;

            if(hayRow == true) {
                cursor.moveToFirst();
                @SuppressLint("Range") int idEncontrado = cursor.getInt(cursor.getColumnIndex(KEY_idAsignatura));
                if (idEncontrado == Integer.parseInt(id))
                {
                    return false;
                }
                else {
                    cursor.close();
                    db.close();
                    return true;
                }
            }

            return true;
    }



    //Insertar Datos iniciales de asignatura
    public void insertarDatosInicialesAsignatura() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + ASIGNATURA_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            agregarAsignaturas("Programación para Dispositivos Móviles", "PDM115", "1");
            agregarAsignaturas("Recursos Humanos", "RHU115", "1");
            agregarAsignaturas("Sistemas de Información Gerencial", "SGI115", "1");
            agregarAsignaturas("Diseño de Sistemas II", "DSI215", "2");
            agregarAsignaturas("Análisis Financiero", "ANF115", "2");
            agregarAsignaturas("Matemática IV", "MAT415", "2");
        }
    }



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
    //Sobrecargado para EditarCicloActivity
    public boolean existeCiclo(String nombre, String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        // Query para ver si ya existe el mismo nombre
        String query = "SELECT * FROM " + CICLO_TABLA + " WHERE " + KEY_nombreCiclo + "=?";
        cursor = db.rawQuery(query, new String[]{nombre});

        // si cursor tiene al menos una row, ciclo ya existe
        boolean rowHay = cursor.getCount() > 0;
        if(rowHay == true) {
            cursor.moveToFirst();
            @SuppressLint("Range") int idEncontrado = cursor.getInt(cursor.getColumnIndex(KEY_idCiclo));
            if (idEncontrado == Integer.parseInt(id)) {
                //el id es el mismo asi que se solo editando el mismo ciclo
                return false;
            } else {
                cursor.close();
                db.close();
                return rowHay;
            }

        }
        return rowHay;

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