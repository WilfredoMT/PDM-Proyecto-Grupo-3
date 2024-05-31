package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.asignar.AgregarAsignacionActivity;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.asignar.EditarAsignacionActivity;

public class AgregarSolicitudesHorarioActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter, adapter2;
    BaseDatosHelper baseDatosHelper;
    private ArrayList<String> dataListMaterias, dataListLocales ;
    private String idCoordinador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_solicitudes_horario);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.agregar_solicitud);

        String nombreCoordinadorLoguado, idUsuarioLoguado, idCoordinadorLoguado;
        Intent intent = getIntent();
        nombreCoordinadorLoguado = intent.getStringExtra("nombreLogueadoCoordinador");
        idUsuarioLoguado = intent.getStringExtra("idUsuarioLoguado");
        idCoordinadorLoguado = intent.getStringExtra("idCoordinadorLoguado");
        idCoordinador= idCoordinadorLoguado;

        Spinner spinnerDias = findViewById(R.id.spinnerDias);
        Spinner spinnerMaterias = findViewById(R.id.SpinnerMaterias);
        TimePicker timePickerFin = findViewById(R.id.timePickerFin);
        TimePicker timePickerInicio = findViewById(R.id.timePickerInicio);
        Spinner spinnerLocal = findViewById(R.id.spinnerLocal);
        Button buttonAgregar = findViewById(R.id.buttonAgregarCiclo);

        EditText editTextNombre = findViewById(R.id.editTextNombre);
        EditText editTextTipo = findViewById(R.id.editTextTipo);

        //llenar spinner materia
        dataListMaterias = new ArrayList<>();
        dataListLocales = new ArrayList<>();
        fetchDatosDB();

        // Crear un ArrayAdapter usando el dataList
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataListMaterias);

        //  set el layout que usar en el spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Usar el adapter
        spinnerMaterias.setAdapter(adapter);


        //llenar spinner de locales
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataListLocales);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocal.setAdapter(adapter2);

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                baseDatosHelper = new BaseDatosHelper(getBaseContext());

                String dias, Materia, horaInicio, minutosInicio, horaFin, minutosFin, local, nombreEvento, tipoEvento;

                dias = spinnerDias.getSelectedItem().toString();
                Materia = spinnerMaterias.getSelectedItem().toString();
                horaInicio = String.valueOf(timePickerInicio.getHour());
                minutosInicio = String.valueOf(timePickerInicio.getMinute());
                horaFin = String.valueOf(timePickerFin.getHour());
                minutosFin = String.valueOf(timePickerFin.getMinute());
                local = spinnerLocal.getSelectedItem().toString();
                nombreEvento = editTextNombre.getText().toString();
                tipoEvento = editTextTipo.getText().toString();

                baseDatosHelper.getAsignatura(Materia, new BaseDatosHelper.Callback() {
                    @Override
                    public boolean onSuccess(Cursor cursor) {
                        String idAsignatura = cursor.getString(0);

                        cursor = baseDatosHelper.getGrupoAsignatura(idAsignatura, null);
                        String idGrupo = cursor.getString(0);


                        final String[] idLocal = new String[1];
                        baseDatosHelper.getLocal(local, new BaseDatosHelper.Callback() {
                            @Override
                            public boolean onSuccess(Cursor cursor) {
                                idLocal[0] = cursor.getString(0);
                                return false;
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, null);


                        String horaInicioHorario = "" + horaInicio+":"+minutosInicio+":00";
                        String horaFinHorario = ""+horaFin+":"+minutosFin+":00";

                        //crear horario
                        String idHorario = baseDatosHelper.agregarHorario(dias, horaInicioHorario, horaFinHorario);


                        //crear propuesta, el valor idEvento solo es para crearlo
                        String idPropuesta = baseDatosHelper.agregarPropuesta("0", "1", "1", idCoordinador);

                        //crear prioridad
                        String idPrioridad = baseDatosHelper.agregarPrioridad("1");

                        //Crear evento
                        String idEvento = baseDatosHelper.agregarEvento(idGrupo, idPropuesta, idHorario, idPrioridad, idLocal[0], nombreEvento, tipoEvento );

                        //actualizar Propuesta para poner idEvento
                        baseDatosHelper.actualizarPropuesta(idPropuesta, "0", "1", idEvento, idCoordinador);

                        Toast.makeText(AgregarSolicitudesHorarioActivity.this, R.string.solicitud_realizada_correctamente, Toast.LENGTH_SHORT).show();
                        finish();
                        return false;
                    }

                    @Override
                    public void onError(String error) {

                    }
                });


            }


        });








    }
    private void fetchDatosDB() {
        // Assuming you have a SQLiteOpenHelper subclass named MyDbHelper
        baseDatosHelper = new BaseDatosHelper(getBaseContext());

        // Open the database for reading
        SQLiteDatabase db = baseDatosHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database you will actually use after this query.
        String[] projection = {
                BaseDatosHelper.KEY_nombreLocal // Replace "columnName" with the actual column name from your database
        };
        String[] projection2 = {
                BaseDatosHelper.KEY_codigoAsignatura // Replace "columnName" with the actual column name from your database
        };

        // Perform a query on the database
        Cursor cursor = db.query(
                BaseDatosHelper.LOCAL_TABLA,   // The table name
                projection,             // The columns to return
                null,          // The columns for the WHERE clause
                null,      // The values for the WHERE clause
                null,          // don't group the rows
                null,           // don't filter by row groups
                null            // The sort order
        );

        Cursor cursor2 = db.rawQuery("SELECT a." + BaseDatosHelper.KEY_codigoAsignatura +
                " FROM " + BaseDatosHelper.ASIGNATURA_TABLA + " AS a" +
                " JOIN " + BaseDatosHelper.GRUPOASIGNATURA_TABLA + " AS g" +
                " ON a." + BaseDatosHelper.KEY_idAsignatura + " = g." + BaseDatosHelper.KEY_idAsignaturaGrupo +
                " WHERE g." + BaseDatosHelper.KEY_idCoordinadorGrupo + " = ?", new String[]{idCoordinador});


        // Iterate through the cursor and populate dataList
        while (cursor.moveToNext()) {
            String data = cursor.getString(cursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_nombreLocal));
            Log.e("AgregarAsignacionActivity - Materis" , data);
            dataListLocales.add(data);
        }

        while (cursor2.moveToNext()) {
            String data2 = cursor2.getString(cursor2.getColumnIndexOrThrow(BaseDatosHelper.KEY_codigoAsignatura));
            dataListMaterias.add(data2);
        }

        // Close the cursor and database
        cursor.close();
        cursor2.close();
        baseDatosHelper.close();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}