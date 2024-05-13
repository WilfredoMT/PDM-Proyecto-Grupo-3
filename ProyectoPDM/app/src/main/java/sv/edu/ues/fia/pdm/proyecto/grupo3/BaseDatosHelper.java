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
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.Calendar;

public class BaseDatosHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DB_NAME = "DBasignaciones";
    private static int DB_VERSION = 1;

    // Nombres de las tablas
    private static final String LOGIN_TABLA = "login";
    public static final String CICLO_TABLA = "ciclo";
    public static final String ASIGNATURA_TABLA = "asignatura";
    public static final String LOCAL_TABLA = "local";
    public static final String COORDINADOR_TABLA = "docenteCoordinador";
    public static final String ESCUELA_TABLA = "escuela";
    public static final String ENCARGADO_TABLA = "encargadoHorario";
    public static final String GRUPOASIGNATURA_TABLA = "grupoAsignatura";
    public static final String ASIGNATURACICLO_TABLA = "asignaturaCiclo";
    public static final String PRIORIDAD_TABLA = "prioridad";
    public static final String HORARIO_TABLA = "horario";
    public static final String DISPONIBILIDAD_TABLA = "disponibilidad";
    public static final String EVENTO_TABLA = "evento";
    public static final String EVENTOLOCAL_TABLA = "eventoLocal";
    public static final String PROPUESTA_TABLA = "propuesta";
    public static final String DETALLEPROPUESTA_TABLA = "detallePropuesta";
    public static final String ASOCIACIONPROPUESTA_TABLA = "asociacionPropuesta";
    public static final String CAMBIOPROPUESTA_TABLA = "cambioPropuesta";



    //------------------------------Atributos grupoAsignatura------------------------------------//
    public static final String KEY_idGrupo = "idGrupo";
    public static final String KEY_idAsignaturaGrupo = "idAsignatura";
    public static final String KEY_idCoordinadorGrupo = "idCoordinador";
    public static final String KEY_totalIntegrantesGrupo = "totalIntegrantes";

    // Tabla Escuela Columnas //
    public static final String KEY_idEscuela = "idEscuela";
    public static final String KEY_nombreEscuela = "nomEscuela";
    public static final String KEY_descripcionEscuela = "descripcionEscuela";


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
    public static final String KEY_idEscuelaLocal = "idEscuela";


    //Tabla DocenteCoordinador columna
    public static final String KEY_idCoordinador = "idCoordinador";
    public static final String KEY_nombreCoordinador = "nombre";
    public static final String KEY_apellidoCoordinador = "apellido";
    public static final String KEY_emailCoordinador = "email";
    public static final String KEY_idUsuarioCoordinador = "idUsuario";

    //Tabla EncargadoHorario columnas
    public static final String KEY_idEncargadoHorario = "idEncargadoHorario";
    public static final String KEY_nombreEncargadoHorario = "nomEncargadoHorario";
    public static final String KEY_apellidoEncargadoHorario = "apellidoHorario";
    public static final String KEY_emailEncargadoHorario = "emailHorario";
    public static final String KEY_idUsuarioEncargadoHorario= "idUsuario";

    //------------------------------Atributos asignaturaCiclo------------------------------------//
    public static final String KEY_idAsignaturaAsignaturaCiclo = "idAsignatura";
    public static final String KEY_idCicloAsignaturaCiclo = "idCiclo";

    //------------------------------Atributos prioridad------------------------------------//
    public static final String KEY_idPrioridad = "idPrioridad";
    public static final String KEY_ordenPrioridad = "orden";

    //------------------------------Atributos horario------------------------------------//
    public static final String KEY_idHorario = "idHorario";
    public static final String KEY_diaHorario = "dia";
    public static final String KEY_horaInicioHorario = "horaInicioHorario";
    public static final String KEY_horaFinHorario = "horaFinHorario";


    //------------------------------Atributos disponibilidad------------------------------------//
    public static final String KEY_idDisponibilidad = "idDisponibilidad";
    public static final String KEY_idLocalDispo = "idLocal";
    public static final String KEY_idHorarioDisponibilidad = "idHorario";
    public static final String KEY_detalleDisponibilidad = "detalleDisponibilidad";

    //------------------------------Atributos evento------------------------------------//
    public static final String KEY_idEvento = "idEvento";
    public static final String KEY_idGrupoEvento = "idGrupo";
    public static final String KEY_idPropuestaEvento = "idPropuesta";
    public static final String KEY_idHorarioEvento = "idHorario";
    public static final String KEY_idPrioridadEvento = "idPrioridad";
    public static final String KEY_idLocalEvento = "idLocal";
    public static final String KEY_nomEvento = "nomEvento";
    public static final String KEY_tipoEvento = "tipoEvento";

    //------------------------------Atributos eventoLocal------------------------------------//
    public static final String KEY_idEventoLocal = "idEvento";
    public static final String KEY_idLocalEventoLocal = "idLocal";

    //------------------------------Atributos propuesta------------------------------------//
    public static final String KEY_idPropuesta = "idPropuesta";
    public static final String KEY_idCambioPropuesta = "idCambio";
    public static final String KEY_idEncargadoHorarioPropuesta = "idEncargadoHorario";
    public static final String KEY_idEncargadoLocalPropuesta = "idEncargadoLocal";
    public static final String KEY_idEventoPropuesta = "idEvento";
    public static final String KEY_idCoordinadorPropuesta = "idCoordinador";

    //------------------------------Atributos asociacionPropuesta ------------------------------------//
    public static final String KEY_idPropuestaAsociacionPropuesta = "idPropuesta";
    public static final String KEY_idDetalleAsociacionPropuesta = "idDetalle";

    //------------------------------Atributos cambioPropuesta------------------------------------//
    public static final String KEY_idCambio = "idCambio";
    public static final String KEY_idPropuestaCambioPropuesta = "idPropuesta";
    public static final String KEY_motivoCambioPropuesta = "motivo";

    //------------------------------Atributos detallePropuesta------------------------------------//
    public static final String KEY_idDetalle = "idDetalle";
    public static final String KEY_idCambioDetallePropuesta = "idCambio";
    public static final String KEY_descripcionDetallePropuesta = "descripcionPropuesta";
    public static final String KEY_horaInicioDetallePropuesta = "horaInicioPropuesta";
    public static final String KEY_horaFinDetallePropuesta = "horaFinPropuesta";

    // Hacer sentencias de creacion SQL //

    //--------------------------------------tabla detallePropuesta-------------------------------------------//
    private static final String CREATE_DETALLEPROPUESTA_TABLA = "CREATE TABLE " + DETALLEPROPUESTA_TABLA + " ("
            + KEY_idDetalle + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_idCambioDetallePropuesta + " INTEGER,"
            + KEY_descripcionDetallePropuesta + " TEXT,"
            + KEY_horaInicioDetallePropuesta + " DATE,"
            + KEY_horaFinDetallePropuesta + " DATE,"
            + "FOREIGN KEY(" + KEY_idCambioDetallePropuesta + ") REFERENCES cambioPropuesta(" + KEY_idCambio + "))" ;

    //--------------------------------------tabla asociacionPropuesta-------------------------------------------//
    private static final String CREATE_ASOCIACIONPROPUESTA_TABLA = "CREATE TABLE " + ASOCIACIONPROPUESTA_TABLA + " ("
            + KEY_idPropuestaAsociacionPropuesta + " INTEGER,"
            + KEY_idDetalleAsociacionPropuesta + " INTEGER,"
            + "FOREIGN KEY(" + KEY_idPropuestaAsociacionPropuesta + ") REFERENCES propuesta(" + KEY_idPropuesta + "),"
            + "FOREIGN KEY(" + KEY_idDetalleAsociacionPropuesta + ") REFERENCES detallePropuesta(" + KEY_idDetalle + "))";

    //--------------------------------------tabla cambioPropuesta-------------------------------------------//
    private static final String CREATE_CAMBIOPROPUESTA_TABLA = "CREATE TABLE " + CAMBIOPROPUESTA_TABLA + " ("
            + KEY_idCambio + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_idPropuestaCambioPropuesta + " INTEGER,"
            + KEY_motivoCambioPropuesta + " TEXT,"
            + "FOREIGN KEY(" + KEY_idPropuestaCambioPropuesta + ") REFERENCES propuesta(" + KEY_idPropuesta + "))" ;

    //--------------------------------------tabla propuesta-------------------------------------------//
    private static final String CREATE_PROPUESTA_TABLA = "CREATE TABLE " + PROPUESTA_TABLA + " ("
            + KEY_idPropuesta + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_idCambioPropuesta + " INTEGER,"
            + KEY_idEncargadoHorarioPropuesta + " INTEGER,"
            + KEY_idEncargadoLocalPropuesta + " INTEGER,"
            + KEY_idEventoPropuesta + " INTEGER,"
            + KEY_idCoordinadorPropuesta + " INTEGER,"
            + "FOREIGN KEY(" + KEY_idCambioPropuesta + ") REFERENCES cambioPropuesta(" + KEY_idCambio + "),"
            + "FOREIGN KEY(" + KEY_idEncargadoHorarioPropuesta + ") REFERENCES encargadoHorario(" + KEY_idEncargadoHorario + "),"
            //+ "FOREIGN KEY(" + KEY_idEncargadoLocalPropuesta + ") REFERENCES encargadoLocal(" + KEY_idEncargadoLocal + "),"
            + "FOREIGN KEY(" + KEY_idEventoPropuesta + ") REFERENCES evento(" + KEY_idEvento + "),"
            + "FOREIGN KEY(" + KEY_idCoordinadorPropuesta + ") REFERENCES docenteCoordinador(" + KEY_idCoordinador + "))" ;

    //--------------------------------------tabla eventoLocal-------------------------------------------//
    private static final String CREATE_EVENTOLOCAL_TABLA = "CREATE TABLE " + EVENTOLOCAL_TABLA + " ("
            + KEY_idEventoLocal + " INTEGER,"
            + KEY_idLocalEventoLocal + " INTEGER,"
            + "FOREIGN KEY(" + KEY_idEventoLocal + ") REFERENCES evento(" + KEY_idAsignatura + "),"
            + "FOREIGN KEY(" + KEY_idLocalEventoLocal + ") REFERENCES local(" + KEY_idCiclo + "))" ;

    private static final String CREATE_EVENTO_TABLA = "CREATE TABLE " + EVENTO_TABLA + " ("
            + KEY_idEvento + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_idGrupoEvento + " INTEGER,"
            + KEY_idPropuestaEvento + " INTEGER,"
            + KEY_idHorarioEvento + " INTEGER,"
            + KEY_idPrioridadEvento + " INTEGER,"
            + KEY_idLocalEvento + " INTEGER,"
            + KEY_nomEvento + " TEXT,"
            + KEY_tipoEvento + " TEXT,"
            + "FOREIGN KEY(" + KEY_idGrupoEvento + ") REFERENCES grupoAsignatura(" + KEY_idGrupo + "),"
            + "FOREIGN KEY(" + KEY_idPropuestaEvento + ") REFERENCES propuesta(" + KEY_idPropuesta + "),"
            + "FOREIGN KEY(" + KEY_idHorarioEvento + ") REFERENCES horario(" + KEY_idHorario + "),"
            + "FOREIGN KEY(" + KEY_idPrioridadEvento + ") REFERENCES prioridad(" + KEY_idPrioridad + ")," 
            + "FOREIGN KEY(" + KEY_idLocalEvento + ") REFERENCES local (" + KEY_idLocal + "))" ;

    //--------------------------------------tabla disponibilidad-------------------------------------------//
    private static final String CREATE_DISPONIBILIDAD_TABLA = "CREATE TABLE " + DISPONIBILIDAD_TABLA + " ("
            + KEY_idDisponibilidad + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_detalleDisponibilidad + " TEXT,"
            + KEY_idLocalDispo + " INTEGER,"
            + KEY_idHorarioDisponibilidad + " INTEGER,"
            + "FOREIGN KEY(" + KEY_idLocalDispo + ") REFERENCES local(" + KEY_idLocal + "),"
            + "FOREIGN KEY(" + KEY_idHorarioDisponibilidad + ") REFERENCES horario(" + KEY_idHorario + "))" ;

    //--------------------------------------tabla horario-------------------------------------------//
    private static final String CREATE_HORARIO_TABLA = "CREATE TABLE " + HORARIO_TABLA + " ("
            + KEY_idHorario + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_diaHorario + " TEXT,"
            + KEY_horaInicioHorario + " DATE,"
            + KEY_horaFinHorario + " DATE)" ;

    //--------------------------------------tabla prioridad-------------------------------------------//
    private static final String CREATE_PRIORIDAD_TABLA = "CREATE TABLE " + PRIORIDAD_TABLA + " ("
            + KEY_idPrioridad + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ordenPrioridad + " INTEGER)";


    //--------------------------------------tabla GrupoAsignatura-------------------------------------------//
    private static final String CREATE_GRUPOASIGNATURA_TABLA = "CREATE TABLE " + GRUPOASIGNATURA_TABLA + " ("
            + KEY_idGrupo + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_idAsignaturaGrupo + " INTEGER,"
            + KEY_idCoordinadorGrupo + " INTEGER,"
            + KEY_totalIntegrantesGrupo + " TEXT,"
            + "FOREIGN KEY(" + KEY_idAsignaturaGrupo + ") REFERENCES asignatura(" + KEY_idAsignatura + "),"
            + "FOREIGN KEY(" + KEY_idCoordinadorGrupo + ") REFERENCES horario(" + KEY_idCoordinador + "))" ;

    //--------------------------------------tabla asignaturaCiclo-------------------------------------------//
    private static final String CREATE_ASIGNATURACICLO_TABLA = "CREATE TABLE " + ASIGNATURACICLO_TABLA + " ("
            + KEY_idAsignaturaAsignaturaCiclo + " INTEGER,"
            + KEY_idCicloAsignaturaCiclo + " INTEGER,"
            + "FOREIGN KEY(" + KEY_idAsignaturaAsignaturaCiclo + ") REFERENCES asignatura(" + KEY_idAsignatura + "),"
            + "FOREIGN KEY(" + KEY_idCicloAsignaturaCiclo + ") REFERENCES ciclo(" + KEY_idCiclo + "))" ;


    //Tabla de escuela
    private static final String CREATE_ESCUELA_TABLA = "CREATE TABLE " + ESCUELA_TABLA + " ("
            + KEY_idEscuela + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_nombreEscuela + " TEXT,"
            + KEY_descripcionEscuela + " TEXT)";

    //Tabla de docenteCoordinador
    private static final String CREATE_COORDINADOR_TABLA = "CREATE TABLE " + COORDINADOR_TABLA + " ("
            + KEY_idCoordinador + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_nombreCoordinador + " TEXT,"
            + KEY_apellidoCoordinador + " TEXT,"
            + KEY_emailCoordinador + " TEXT,"
            + KEY_idUsuarioCoordinador + " INTEGER,"
            + "FOREIGN KEY(" + KEY_idUsuarioCoordinador + ") REFERENCES login(" + KEY_idUsuario + "))" ;

    //Tabla de EncargadoHorario
    private static final String CREATE_ENCARGADO_TABLA = "CREATE TABLE " + ENCARGADO_TABLA + " ("
            + KEY_idEncargadoHorario + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_nombreEncargadoHorario + " TEXT,"
            + KEY_apellidoEncargadoHorario + " TEXT,"
            + KEY_emailEncargadoHorario + " TEXT,"
            + KEY_idUsuarioEncargadoHorario + " INTEGER,"
            + "FOREIGN KEY(" + KEY_idUsuarioEncargadoHorario + ") REFERENCES login(" + KEY_idUsuario + "))" ;

    //Tabla de locales
    private static final String CREATE_LOCAL_TABLA = "CREATE TABLE " + LOCAL_TABLA + " ("
            + KEY_idLocal + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_nombreLocal + " TEXT,"
            + KEY_tipoLocal + " TEXT,"
            + KEY_capacidadLocal + " INTEGER,"
            + KEY_idEscuelaLocal + " INTEGER,"
            + "FOREIGN KEY(" + KEY_idEscuelaLocal + ") REFERENCES escuela(" + KEY_idEscuela + "))" ;

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
        db.execSQL(CREATE_ESCUELA_TABLA);
        db.execSQL(CREATE_COORDINADOR_TABLA);
        db.execSQL(CREATE_ENCARGADO_TABLA);
        db.execSQL(CREATE_LOCAL_TABLA);
        db.execSQL(CREATE_ASIGNATURACICLO_TABLA);
        db.execSQL(CREATE_GRUPOASIGNATURA_TABLA);
        db.execSQL(CREATE_HORARIO_TABLA);
        db.execSQL(CREATE_PRIORIDAD_TABLA);
        db.execSQL(CREATE_DISPONIBILIDAD_TABLA);
        db.execSQL(CREATE_DETALLEPROPUESTA_TABLA);
        db.execSQL(CREATE_CAMBIOPROPUESTA_TABLA);
        db.execSQL(CREATE_ASOCIACIONPROPUESTA_TABLA);
        db.execSQL(CREATE_PROPUESTA_TABLA);
        db.execSQL(CREATE_EVENTO_TABLA);



    }

    /*
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar tablas si existen
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + CICLO_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + ASIGNATURA_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + LOCAL_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + COORDINADOR_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + ESCUELA_TABLA);

        // Crear tablas de nuevo
        onCreate(db);
    }

     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Log.d("DatabaseUpgrade", "onUpgrade called. Old version: " + oldVersion + ", New version: " + newVersion);

            // Eliminar tablas si existen
            db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLA);
            db.execSQL("DROP TABLE IF EXISTS " + CICLO_TABLA);
            db.execSQL("DROP TABLE IF EXISTS " + ASIGNATURA_TABLA);
            db.execSQL("DROP TABLE IF EXISTS " + ESCUELA_TABLA);
            db.execSQL("DROP TABLE IF EXISTS " + LOCAL_TABLA);
            db.execSQL("DROP TABLE IF EXISTS " + COORDINADOR_TABLA);
            db.execSQL("DROP TABLE IF EXISTS " + ENCARGADO_TABLA);

            // Luego, puedes ejecutar los scripts de creación de tablas o realizar cualquier otra actualización necesaria aquí

        } catch (Exception e) {
            Log.e("DatabaseUpgrade", "Error upgrading database: " + e.getMessage());
            // Manejar el error según sea necesario
        }
    }

    //                    Helpers para CRUDs                  //

//Tabla Prioridad//

    //agregar prioridad
    public String agregarPrioridad(String orden) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ordenPrioridad, orden);
        long id = db.insert(PRIORIDAD_TABLA, null, values);
        db.close();
        return String.valueOf(id);
    }

    //Get Escuela
    public Cursor getPrioridad(String id, String nulll) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PRIORIDAD_TABLA, new String[]{KEY_idPrioridad,
                        KEY_ordenPrioridad}, KEY_idPrioridad + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }
    //sobrecargar para usar nombre
    public Cursor getPrioridad(String orden) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PRIORIDAD_TABLA, new String[]{KEY_idPrioridad,
                        KEY_ordenPrioridad}, KEY_ordenPrioridad + "=?",
                new String[]{orden}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar prioridad
    public boolean actualizarPrioridad(String idPrioridad, String orden) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ordenPrioridad, orden);
        int rowsAffected = db.update(PRIORIDAD_TABLA, values, KEY_idPrioridad + " = ?", new String[]{idPrioridad});
        return rowsAffected > 0;
    }

    //Eliminar prioridad
    public boolean eliminarPrioridad(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(PRIORIDAD_TABLA, KEY_idPrioridad + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    //Ver si ya existe prioridad

    /*
    public boolean existePrioridad(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Query para ver si ya existe el mismo codigo
            String query = "SELECT * FROM " + ESCUELA_TABLA + " WHERE " + KEY_nombreEscuela + "=?";
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
    public boolean existePrioridad(String nombre, String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        // Query para ver si ya existe el mismo nombre
        String query = "SELECT * FROM " + ESCUELA_TABLA + " WHERE " + KEY_nombreEscuela + "=?";
        cursor = db.rawQuery(query, new String[]{nombre});

        // si cursor tiene al menos una row, ciclo ya existe
        boolean rowHay = cursor.getCount() > 0;
        if(rowHay == true) {
            cursor.moveToFirst();
            @SuppressLint("Range") int idEncontrado = cursor.getInt(cursor.getColumnIndex(KEY_idEscuela));
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

     */

    //Insertar Datos iniciales de Prioridad
    public void insertarDatosInicialesPrioridad() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + PRIORIDAD_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            agregarPrioridad("1");
        }
    }


    //Tabla  GrupoAsignatura //

    //agregar grupoAsignatura
    public boolean agregarGrupoAsignatura(String idAsignatura, String idCoordinador, String totalIntegrantes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_idAsignaturaGrupo, idAsignatura);
        values.put(KEY_idCoordinadorGrupo, idCoordinador);
        values.put(KEY_totalIntegrantesGrupo, totalIntegrantes);
        long result = db.insert(GRUPOASIGNATURA_TABLA, null, values);
        return result != -1;
    }

    //Get grupoAsignatura
    public Cursor getGrupoAsignatura(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(GRUPOASIGNATURA_TABLA, new String[]{KEY_idGrupo,
                        KEY_idAsignaturaGrupo, KEY_idCoordinadorGrupo, KEY_totalIntegrantesGrupo}, KEY_idGrupo + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }
    public Cursor getGrupoAsignatura(String idAsignatura, String nulll) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(GRUPOASIGNATURA_TABLA, new String[]{KEY_idGrupo,
                        KEY_idAsignaturaGrupo, KEY_idCoordinadorGrupo, KEY_totalIntegrantesGrupo}, KEY_idAsignaturaGrupo + "=?",
                new String[]{idAsignatura}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar grupoAsigntura
    public boolean actualizarGrupoAsignatura(String idGrupo, String idAsignaturaGrupo, String idCoordinadorGrupo, String totalIntegrantes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_idAsignaturaGrupo, idAsignaturaGrupo);
        values.put(KEY_idCoordinadorGrupo, idCoordinadorGrupo);
        values.put(KEY_totalIntegrantesGrupo, totalIntegrantes);
        int rowsAffected = db.update(GRUPOASIGNATURA_TABLA, values, KEY_idGrupo + " = ?", new String[]{idGrupo});
        return rowsAffected > 0;
    }

    //Eliminar grupoAsignatura
    public boolean eliminarGrupoAsignatura(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(GRUPOASIGNATURA_TABLA, KEY_idGrupo + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    /*
    //Ver si ya existe coordinador registrado
    public boolean existeCoordinador(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Query para ver si ya existe el mismo codigo
            String query = "SELECT * FROM " + COORDINADOR_TABLA + " WHERE " + KEY_nombreCoordinador + "=?";
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
    public boolean existeCoordinador(String nombre, String id) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        // Query para ver si ya existe el mismo nombre
        String query = "SELECT * FROM " + COORDINADOR_TABLA + " WHERE " + KEY_nombreCoordinador + "=?";
        cursor = db.rawQuery(query, new String[]{nombre});

        // si cursor tiene al menos una row, ciclo ya existe
        boolean rowHay = cursor.getCount() > 0;
        if(rowHay == true) {
            cursor.moveToFirst();
            @SuppressLint("Range") int idEncontrado = cursor.getInt(cursor.getColumnIndex(KEY_idCoordinador));
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

     */



    //Insertar Datos iniciales de GrupoAsignatura
    public void insertarDatosInicialesGrupoAsignatura() {


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + GRUPOASIGNATURA_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            agregarGrupoAsignatura("5", "1", "20");
        }
    }


    //Tabla Escuela//

    //agregar local
    public boolean agregarEscuela(String nombre, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreEscuela, nombre);
        values.put(KEY_descripcionEscuela, descripcion);
        long result = db.insert(ESCUELA_TABLA, null, values);
        return result != -1;
    }

    //Get Escuela
    public Cursor getEscuela(String id, String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ESCUELA_TABLA, new String[]{KEY_idEscuela,
                        KEY_nombreEscuela, KEY_descripcionEscuela}, KEY_idEscuela + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }
    //sobrecargar para usar nombre
    public Cursor getEscuela(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ESCUELA_TABLA, new String[]{KEY_idEscuela,
                        KEY_nombreEscuela, KEY_descripcionEscuela}, KEY_nombreEscuela + "=?",
                new String[]{nombre}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar Escuela
    public boolean actualizarEscuela(String idEscuela, String nombre, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreEscuela, nombre);
        values.put(KEY_descripcionEscuela, descripcion);
        int rowsAffected = db.update(ESCUELA_TABLA, values, KEY_idEscuela + " = ?", new String[]{idEscuela});
        return rowsAffected > 0;
    }

    //Eliminar Escuela
    public boolean eliminarEscuela(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(ESCUELA_TABLA, KEY_idEscuela + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    //Ver si ya existe Escuela
    public boolean existeEscuela(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Query para ver si ya existe el mismo codigo
            String query = "SELECT * FROM " + ESCUELA_TABLA + " WHERE " + KEY_nombreEscuela + "=?";
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
    public boolean existeEscuela(String nombre, String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        // Query para ver si ya existe el mismo nombre
        String query = "SELECT * FROM " + ESCUELA_TABLA + " WHERE " + KEY_nombreEscuela + "=?";
        cursor = db.rawQuery(query, new String[]{nombre});

        // si cursor tiene al menos una row, ciclo ya existe
        boolean rowHay = cursor.getCount() > 0;
        if(rowHay == true) {
            cursor.moveToFirst();
            @SuppressLint("Range") int idEncontrado = cursor.getInt(cursor.getColumnIndex(KEY_idEscuela));
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



    //Insertar Datos iniciales de Escuela
    public void insertarDatosInicialesEscuela() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + ESCUELA_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            agregarEscuela("Sistemas informaticos", "Edificio de sistemas");
            agregarEscuela("Ingenieria electrica", "Edificio de electrica");
        }
    }
                         //Tabla Encargado de horario//

    //agregar EncargadoHorario
    public boolean agregarEncargado(String nombre, String apellido, String email, String idUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreEncargadoHorario, nombre);
        values.put(KEY_apellidoEncargadoHorario, apellido);
        values.put(KEY_emailEncargadoHorario, email);
        values.put(KEY_idUsuarioEncargadoHorario, idUsuario);
        long result = db.insert(ENCARGADO_TABLA, null, values);
        return result != -1;
    }

    //Get EncargadoHorario
    public Cursor getEncargado(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ENCARGADO_TABLA, new String[]{KEY_idEncargadoHorario,
                        KEY_nombreEncargadoHorario, KEY_apellidoEncargadoHorario,KEY_emailEncargadoHorario, KEY_idUsuarioEncargadoHorario}, KEY_idEncargadoHorario + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar EncargadoHorario
    public boolean actualizarEncargado(String idEncargadoHorario, String nombre, String apellido, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreEncargadoHorario, nombre);
        values.put(KEY_apellidoEncargadoHorario, apellido);
        values.put(KEY_emailEncargadoHorario, email);
        int rowsAffected = db.update(ENCARGADO_TABLA, values, KEY_idEncargadoHorario + " = ?", new String[]{idEncargadoHorario});
        return rowsAffected > 0;
    }

    //Eliminar EncargadoHorario
    public boolean eliminarEncargado(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(ENCARGADO_TABLA, KEY_idEncargadoHorario + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    //Ver si ya existe EncargadoHorario registrado
    public boolean existeEncargado(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Query para ver si ya existe el mismo codigo
            String query = "SELECT * FROM " + ENCARGADO_TABLA + " WHERE " + KEY_nombreEncargadoHorario + "=?";
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
    public boolean existeEncargado(String nombre, String id) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        // Query para ver si ya existe el mismo nombre
        String query = "SELECT * FROM " + ENCARGADO_TABLA + " WHERE " + KEY_nombreEncargadoHorario + "=?";
        cursor = db.rawQuery(query, new String[]{nombre});

        // si cursor tiene al menos una row, ciclo ya existe
        boolean rowHay = cursor.getCount() > 0;
        if(rowHay == true) {
            cursor.moveToFirst();
            @SuppressLint("Range") int idEncontrado = cursor.getInt(cursor.getColumnIndex(KEY_idEncargadoHorario));
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



    //Insertar Datos iniciales de EncargadoHorario
    public void insertarDatosInicialesEncargado() {


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + ENCARGADO_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            agregarEncargado("nombreEncargadoHorario", "apellidoEncargadoHorario", "encargado@ues.edu.sv", "3");
        }
    }


                           //Tabla Coordinador//

    //agregar coordinador
    public boolean agregarCoord(String nombre, String apellido, String email, String idUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreCoordinador, nombre);
        values.put(KEY_apellidoCoordinador, apellido);
        values.put(KEY_emailCoordinador, email);
        values.put(KEY_idUsuarioCoordinador, idUsuario);
        long result = db.insert(COORDINADOR_TABLA, null, values);
        return result != -1;
    }

    //Get coordinador
    public Cursor getCoordinador(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(COORDINADOR_TABLA, new String[]{KEY_idCoordinador,
                        KEY_nombreCoordinador, KEY_apellidoCoordinador,KEY_emailCoordinador, KEY_idUsuarioCoordinador}, KEY_idCoordinador + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }
    //sobrecargado nombre
    public Cursor getCoordinador(String nombreCoordinador, String nulll) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(COORDINADOR_TABLA, new String[]{KEY_idCoordinador,
                        KEY_nombreCoordinador, KEY_apellidoCoordinador,KEY_emailCoordinador, KEY_idUsuarioCoordinador}, KEY_nombreCoordinador + "=?",
                new String[]{nombreCoordinador}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }
    //sobrecargado idusuario
    public Cursor getCoordinador(String idUsuario, String nullll, String nulll) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(COORDINADOR_TABLA, new String[]{KEY_idCoordinador,
                        KEY_nombreCoordinador, KEY_apellidoCoordinador,KEY_emailCoordinador, KEY_idUsuarioCoordinador}, KEY_idUsuarioCoordinador + "=?",
                new String[]{idUsuario}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar coordinador
    public boolean actualizarCoordinador(String idCoordinador, String nombre, String apellido, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreCoordinador, nombre);
        values.put(KEY_apellidoCoordinador, apellido);
        values.put(KEY_emailCoordinador, email);
        int rowsAffected = db.update(COORDINADOR_TABLA, values, KEY_idCoordinador + " = ?", new String[]{idCoordinador});
        return rowsAffected > 0;
    }

    //Eliminar coordinador
    public boolean eliminarCoordinador(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(COORDINADOR_TABLA, KEY_idCoordinador + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    //Ver si ya existe coordinador registrado
    public boolean existeCoordinador(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Query para ver si ya existe el mismo codigo
            String query = "SELECT * FROM " + COORDINADOR_TABLA + " WHERE " + KEY_nombreCoordinador + "=?";
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
    public boolean existeCoordinador(String nombre, String id) {


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        // Query para ver si ya existe el mismo nombre
        String query = "SELECT * FROM " + COORDINADOR_TABLA + " WHERE " + KEY_nombreCoordinador + "=?";
        cursor = db.rawQuery(query, new String[]{nombre});

        // si cursor tiene al menos una row, ciclo ya existe
        boolean rowHay = cursor.getCount() > 0;
        if(rowHay == true) {
            cursor.moveToFirst();
            @SuppressLint("Range") int idEncontrado = cursor.getInt(cursor.getColumnIndex(KEY_idCoordinador));
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



    //Insertar Datos iniciales de coordinador
    public void insertarDatosInicialesCoord() {


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + COORDINADOR_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            agregarCoord("nombreCoordinador", "apellidoCoordinador", "coordinador@ues.edu.sv", "2");
        }
    }


    //Tabla locales//

    //agregar local
    public boolean agregarLocal(String nombre, String tipo, String capacidad, String idEscuela) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreLocal, nombre);
        values.put(KEY_tipoLocal, tipo);
        values.put(KEY_capacidadLocal, capacidad);
        values.put(KEY_idEscuelaLocal, idEscuela);
        long result = db.insert(LOCAL_TABLA, null, values);
        return result != -1;
    }

    //Get Local
    public Cursor getLocal(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(LOCAL_TABLA, new String[]{KEY_idLocal,
                        KEY_nombreLocal, KEY_tipoLocal, KEY_capacidadLocal, KEY_idEscuelaLocal}, KEY_idLocal + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }
    public Cursor getLocal(String nombre, String nulll) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(LOCAL_TABLA, new String[]{KEY_idLocal,
                        KEY_nombreLocal, KEY_tipoLocal, KEY_capacidadLocal, KEY_idEscuelaLocal}, KEY_nombreLocal + "=?",
                new String[]{nombre}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar local
    public boolean actualizarLocal(String idLocal, String nombre, String tipo, String capacidad, String idEscuela) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreLocal, nombre);
        values.put(KEY_tipoLocal, tipo);
        values.put(KEY_capacidadLocal, capacidad);
        values.put(KEY_idEscuelaLocal, idEscuela);
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

        // Query para ver si ya existe el mismo nombre
        String query = "SELECT * FROM " + LOCAL_TABLA + " WHERE " + KEY_nombreLocal + "=?";
        cursor = db.rawQuery(query, new String[]{nombre});

        // si cursor tiene al menos una row, ciclo ya existe
        boolean rowHay = cursor.getCount() > 0;
        if(rowHay == true) {
            cursor.moveToFirst();
            @SuppressLint("Range") int idEncontrado = cursor.getInt(cursor.getColumnIndex(KEY_idLocal));
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



    //Insertar Datos iniciales de local
    public void insertarDatosInicialesLocal() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + LOCAL_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            //agregarLocal("Miguel Marmol", "Auditorio", "100");
            agregarLocal("Infocentros", "Salon", "25", "1");
            agregarLocal("Salon A", "Salon", "25", "2");
            //agregarLocal("Edificio C", "Edificio", "120");

        }
    }


//Tabla Horario//

    //agregar Horario
    public String agregarHorario(String dia, String horaInicioHorario, String horaFinHorario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_diaHorario, dia);
        values.put(KEY_horaInicioHorario, horaInicioHorario);
        values.put(KEY_horaFinHorario, horaFinHorario);
        long id = db.insert(HORARIO_TABLA, null, values);
        return String.valueOf(id);
    }

    //Get Horario
    public Cursor getHorario(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(HORARIO_TABLA, new String[]{KEY_idHorario,
                        KEY_diaHorario, KEY_horaInicioHorario, KEY_horaFinHorario}, KEY_idHorario + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar Horario
    public boolean actualizarHorario(String idHorario, String dia, String horaInicioHorario, String horaFinHorario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_diaHorario, dia);
        values.put(KEY_horaInicioHorario, horaInicioHorario);
        values.put(KEY_horaFinHorario, horaFinHorario);
        int rowsAffected = db.update(HORARIO_TABLA, values, KEY_idHorario + " = ?", new String[]{idHorario});
        return rowsAffected > 0;
    }

    //Eliminar Horario
    public boolean eliminarHorario(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(HORARIO_TABLA, KEY_idHorario + " = ?", new String[]{id});
        return rowsAffected > 0;
    }


    //Insertar Datos iniciales
    public void insertarDatosInicialesHorario() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + HORARIO_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            agregarHorario("Lunes", "06:20:00", "08:00:00");
            agregarHorario("Martes", "06:20:00", "06:20:00");
            agregarHorario("Miercoles", "06:20:00", "06:20:00");
            agregarHorario("Jueeves", "06:20:00", "06:20:00");
            agregarHorario("Viernes", "06:20:00", "06:20:00");
        }
    }

    //Tabla propuesta//

    //agregar propuesta
    public String agregarPropuesta(String idCambio, String idEncargadoHorario, String idEvento , String idDocenteCoordinador) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_idCambioPropuesta, idCambio);
        values.put(KEY_idEncargadoHorarioPropuesta, idEncargadoHorario);
        values.put(KEY_idEventoPropuesta, idEvento);
        values.put(KEY_idCoordinadorPropuesta, idDocenteCoordinador);
        long id = db.insert(PROPUESTA_TABLA, null, values);
        return  String.valueOf(id);
    }

    //Get Propuesta
    public Cursor getPropuesta(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PROPUESTA_TABLA, new String[]{KEY_idPropuesta,
                        KEY_idCambioPropuesta, KEY_idEncargadoLocalPropuesta, KEY_idEventoPropuesta, KEY_idCoordinadorPropuesta}, KEY_idPropuesta + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar Propuesta
    public boolean actualizarPropuesta(String idPropuesta, String idCambio, String idEncargadoHorario, String idEvento , String idDocenteCoordinador) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_idCambioPropuesta, idCambio);
        values.put(KEY_idEncargadoHorarioPropuesta, idEncargadoHorario);
        values.put(KEY_idEventoPropuesta, idEvento);
        values.put(KEY_idCoordinadorPropuesta, idDocenteCoordinador);
        int rowsAffected = db.update(PROPUESTA_TABLA, values, KEY_idPropuesta + " = ?", new String[]{idPropuesta});
        return rowsAffected > 0;
    }

    //Eliminar Ciclo
    public boolean eliminarPropuesta(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(PROPUESTA_TABLA, KEY_idPropuesta + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    //Insertar Datos iniciales
    public void insertarDatosInicialesPropuesta() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + PROPUESTA_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            agregarPropuesta("1", "1", "1", "1");
            agregarPropuesta("1", "1", "2", "2");
            //agregarPropuesta("01", "01", "03", "01");
            //agregarPropuesta("01", "01", "04", "01");

        }
    }

    //Tabla DetallePropuesta//

    //agregar DetallePropuesta
    public boolean agregarDetallePropuesta(String idCambio, String descripcion, String horaInicio, String horaFin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_idCambioDetallePropuesta, idCambio);
        values.put(KEY_descripcionDetallePropuesta, descripcion);
        values.put(KEY_horaInicioDetallePropuesta, horaInicio);
        values.put(KEY_horaFinDetallePropuesta, horaFin);
        long result = db.insert(DETALLEPROPUESTA_TABLA, null, values);
        return result != -1;
    }

    //Get DetallePropuesta
    public Cursor getDetallePropuesta(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DETALLEPROPUESTA_TABLA, new String[]{KEY_idDetalle,
                        KEY_idCambioDetallePropuesta, KEY_descripcionDetallePropuesta, KEY_horaInicioDetallePropuesta, KEY_horaFinDetallePropuesta}, KEY_idDetalle + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar DetallePropuesta
    public boolean actualizarDetallePropuesta(String idDetalle,  String descripcion, String horaInicio, String horaFin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_descripcionDetallePropuesta, descripcion);
        values.put(KEY_horaInicioDetallePropuesta, horaInicio);
        values.put(KEY_horaFinDetallePropuesta, horaFin);
        int rowsAffected = db.update(DETALLEPROPUESTA_TABLA, values, KEY_idDetalle + " = ?", new String[]{idDetalle});
        return rowsAffected > 0;
    }

    //Eliminar DetallePropuesta
    public boolean eliminarDetallePropuesta(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(DETALLEPROPUESTA_TABLA, KEY_idDetalle + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    //Insertar Datos iniciales
    public void insertarDatosInicialesDetallePropuesta() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DETALLEPROPUESTA_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            agregarDetallePropuesta("01","Es unca clase Terorica de PDM115","06:20:00","8:00:00");
            agregarDetallePropuesta("01","Es unca clase de Discucion de PDM115","06:20:00","8:00:00");

        }
    }
//Tabla ciclo//

    //agregar ciclo
    public boolean agregarAsociacionPropuesta(String idPropuesta, String idDetalle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_idPropuestaAsociacionPropuesta, idPropuesta);
        values.put(KEY_idDetalleAsociacionPropuesta, idDetalle);

        long result = db.insert(ASOCIACIONPROPUESTA_TABLA, null, values);
        return result != -1;
    }

    //Insertar Datos iniciales
    public void insertarDatosInicialesAsociacionPropuesta() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + ASOCIACIONPROPUESTA_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            agregarAsociacionPropuesta("01", "01");
            agregarAsociacionPropuesta("02", "02");


        }
    }


    //Tabla de Evento//
    //agregar evento
    public String agregarEvento(String idGrupo, String idPropuesta, String idHorario, String idPrioridad, String idLocal , String nomEvento, String tipoEvento){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nomEvento, nomEvento);
        values.put(KEY_tipoEvento, tipoEvento);
        values.put(KEY_idGrupoEvento, idGrupo);
        values.put(KEY_idPropuestaEvento, idPropuesta);
        values.put(KEY_idHorarioEvento, idHorario);
        values.put(KEY_idPrioridadEvento, idPrioridad);
        values.put(KEY_idLocalEvento, idLocal);
        long id = db.insert(EVENTO_TABLA, null, values);
        return String.valueOf(id);
    }
    //Get Evento
    public Cursor getEvento(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EVENTO_TABLA, new String[]{KEY_idEvento, KEY_idGrupoEvento,
                        KEY_idPropuestaEvento, KEY_idHorarioEvento, KEY_idPrioridadEvento,
                        KEY_idLocalEvento , KEY_nomEvento, KEY_tipoEvento,
                        }, KEY_idEvento + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar Evento
    public boolean actualizarEvento(String idEvento, String idGrupo, String idPropuesta, String idHorario, String idPrioridad, String idLocal , String nomEvento, String tipoEvento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_idGrupoEvento, idGrupo);
        values.put(KEY_idPropuestaEvento, idPropuesta);
        values.put(KEY_idHorarioEvento, idHorario);
        values.put(KEY_idPropuestaEvento, idPrioridad);
        values.put(KEY_idLocalEvento, idLocal);
        values.put(KEY_nomEvento, nomEvento);
        values.put(KEY_tipoEvento, tipoEvento);
        int rowsAffected = db.update(EVENTO_TABLA, values, KEY_idEvento + " = ?", new String[]{idEvento});
        return rowsAffected > 0;
    }

    //Eliminar Evento
    public boolean eliminarEvento(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(EVENTO_TABLA, KEY_idEvento + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    //Ver si ya existe Evento registrado
    public boolean existeEvento(String nomEvento) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Query para ver si ya existe el mismo nombre
            String query = "SELECT * FROM " + EVENTO_TABLA + " WHERE " + KEY_nomEvento + "=?";
            cursor = db.rawQuery(query, new String[]{nomEvento});


            // si cursor tiene al menos una row, evento ya existe
            return cursor.getCount() > 0;
        } finally {
            // cerrar cursor y db
            if (cursor != null)
                cursor.close();
            db.close();
        }
    }

    //Sobrecargado para EditarEventoActivity
    public boolean existeEvento(String nomEvento, String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        // Query para ver si ya existe el mismo nombre
        String query = "SELECT * FROM " + EVENTO_TABLA + " WHERE " + KEY_nomEvento + "=?";
        cursor = db.rawQuery(query, new String[]{nomEvento});

        // si cursor tiene al menos una row, ciclo ya existe
        boolean rowHay = cursor.getCount() > 0;
        if(rowHay == true) {
            cursor.moveToFirst();
            @SuppressLint("Range") int idEncontrado = cursor.getInt(cursor.getColumnIndex(KEY_idEvento));
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
    public void insertarDatosInicialesEvento() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + EVENTO_TABLA, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            agregarEvento("1", "1", "1", "1", "1", "Clase de grupo de discucion #01" , "Discusion");
            agregarEvento("2", "2", "2", "1", "2", "Clase de grupo de discucion 02", "Clase");
            //agregarEvento("PDM115 GD 02", "Clase de grupo de discucion #02","1", "3", "5", "3");
            //agregarEvento("PDM115 GD 03", "Clase de grupo de discucion #03", "1", "4", "9", "4");
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
    public Cursor getAsignatura(String codigo, String nulll) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ASIGNATURA_TABLA, new String[]{KEY_idAsignatura,
                        KEY_nombreAsignatura, KEY_codigoAsignatura, KEY_numeroCicloAsignatura}, KEY_codigoAsignatura + "=?",
                new String[]{codigo}, null, null, null, null);

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
    public long agregarUsuario(String nombreusuario, String clave, String rol, Bitmap imagenpefil) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreUsuario, nombreusuario);
        values.put(KEY_clave, clave);
        values.put(KEY_rol, rol);
        values.put(KEY_imagen_perfil, getBytesFromBitmap(imagenpefil));

        long id = db.insert(LOGIN_TABLA, null, values);
        //long result = db.insert(LOGIN_TABLA, null, values);
        db.close();
        return id;
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
    public Cursor getUsuario(String id, String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(LOGIN_TABLA, new String[]{KEY_idUsuario,
                        KEY_nombreUsuario, KEY_clave, KEY_rol, KEY_imagen_perfil}, KEY_idUsuario + "=?",
                new String[]{id}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }
    //Sobrecargar
    public Cursor getUsuario(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(LOGIN_TABLA, new String[]{KEY_idUsuario,
                        KEY_nombreUsuario, KEY_clave, KEY_imagen_perfil, KEY_rol}, KEY_nombreUsuario + "=?",
                new String[]{nombre}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    //Actualizar Usuario
    public boolean actualizarUsuario(String idUsuario, String nombreusuario, String clave, String rol, Bitmap perfil) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_nombreUsuario, nombreusuario);
        values.put(KEY_clave, clave);
        values.put(KEY_rol, rol);
        values.put(KEY_imagen_perfil, getBytesFromBitmap(perfil));
        int rowsAffected = db.update(LOGIN_TABLA, values, KEY_idUsuario + " = ?", new String[]{idUsuario});
        return rowsAffected > 0;
    }

    //Eliminar Usuario
    public boolean eliminarUsuario(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(LOGIN_TABLA, KEY_idUsuario + " = ?", new String[]{id});
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
            agregarUsuario("UsuarioEncargado", "PDM115", "Encargado de Horario", BitmapFactory.decodeResource(context.getResources(), R.drawable.docente));
        }
    }














}