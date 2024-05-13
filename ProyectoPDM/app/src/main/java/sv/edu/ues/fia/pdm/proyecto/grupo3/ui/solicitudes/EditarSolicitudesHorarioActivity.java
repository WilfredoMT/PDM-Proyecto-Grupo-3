package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

public class EditarSolicitudesHorarioActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter, adapter2;
    BaseDatosHelper baseDatosHelper;
    private ArrayList<String> dataListMaterias, dataListLocales;
    private String idCoordinador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_solicitudes_horario);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.editar_solicitud);

        String idEventoEditar, nombreEvento, tipoEvento;
        Intent intent = getIntent();

        idEventoEditar = intent.getStringExtra("idEditar");
        idCoordinador = intent.getStringExtra("idCoordinador");


        Spinner spinnerDias = findViewById(R.id.spinnerDias);
        Spinner spinnerMaterias = findViewById(R.id.SpinnerMaterias);
        TimePicker timePickerFin = findViewById(R.id.timePickerFin);
        TimePicker timePickerInicio = findViewById(R.id.timePickerInicio);
        Spinner spinnerLocal = findViewById(R.id.spinnerLocal);
        Button buttonAgregar = findViewById(R.id.buttonAgregarCiclo);

        EditText editTextNombre = findViewById(R.id.editTextNombre);
        EditText editTextTipo = findViewById(R.id.editTextTipo);

        baseDatosHelper = new BaseDatosHelper(getBaseContext());

        Cursor cursor = baseDatosHelper.getEvento(idEventoEditar);

        tipoEvento = cursor.getString(7);
        nombreEvento = cursor.getString(6);
        editTextNombre.setText(nombreEvento);
        editTextTipo.setText(tipoEvento);
        String idHorarioEditar = cursor.getString(3);
        String idGrupo = cursor.getString(1);
        String idLocal = cursor.getString(5);
        String idPropuesta = cursor.getString(2);
        String idEvento = cursor.getString(0);
        String idPrioridad = cursor.getString(4);


        //recuperar local
        cursor = baseDatosHelper.getLocal(idLocal);
        String local = cursor.getString(1);

        //recuperar materias

        cursor = baseDatosHelper.getGrupoAsignatura(idGrupo);
        String idAsignatura = cursor.getString(1);
        cursor = baseDatosHelper.getAsignatura(idAsignatura);
        String codigoMateria = cursor.getString(2);


        //recuperar dias
        cursor = baseDatosHelper.getHorario(idHorarioEditar);
        String dia = cursor.getString(1);
        String HoraInicio = cursor.getString(2);
        String HoraFin = cursor.getString(3);
        //split datos de dia
        String[] partesHoraInicio = HoraInicio.split(":");
        String horasInicio = partesHoraInicio[0];
        String minutosInicio = partesHoraInicio[1];

        String[] partesHoraFin = HoraFin.split(":");
        String horasFin = partesHoraFin[0];
        String minutosFin = partesHoraFin[1];

        //Setear timepickers
        timePickerInicio.setHour(Integer.parseInt(horasInicio));
        timePickerInicio.setMinute(Integer.parseInt(minutosInicio));
        timePickerFin.setHour(Integer.parseInt(horasFin));
        timePickerFin.setMinute(Integer.parseInt(minutosFin));


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


        //seleccionar spinners
        if (adapter != null) {
            // Iterar hasta encontrar la posicion
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).equals(codigoMateria)) {
                    // Set la seleccion
                    spinnerMaterias.setSelection(i);
                    break;
                }
            }
        }
        if (adapter2 != null) {
            // Iterar hasta encontrar la posicion
            for (int i = 0; i < adapter2.getCount(); i++) {
                if (adapter2.getItem(i).equals(local)) {
                    // Set la seleccion
                    spinnerLocal.setSelection(i);
                    break;
                }
            }
        }

        //llenar spinner de dias

        Context context = this;
        Resources resources = context.getResources();

        String Lunes = resources.getStringArray(R.array.dias)[0];
        String Martes = resources.getStringArray(R.array.dias)[1];
        String Miercoles = resources.getStringArray(R.array.dias)[2];
        String Jueves = resources.getStringArray(R.array.dias)[3];
        String Viernes = resources.getStringArray(R.array.dias)[4];
        String Sabado = resources.getStringArray(R.array.dias)[5];
        String Domingo = resources.getStringArray(R.array.dias)[6];

        if (dia.equals(Lunes)) {
            spinnerDias.setSelection(0);
        } else if (dia.equals(Martes)) {
            spinnerDias.setSelection(1);
        } else if (dia.equals(Miercoles)) {
            spinnerDias.setSelection(2);
        } else if (dia.equals(Jueves)) {
            spinnerDias.setSelection(3);
        } else if (dia.equals(Viernes)) {
            spinnerDias.setSelection(4);
        }else if (dia.equals(Sabado)){
            spinnerDias.setSelection(5);
        } else if (dia.equals(Domingo)) {
            spinnerDias.setSelection(6);
        }


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


                String horaInicioHorario = "" + horaInicio + ":" + minutosInicio + ":00";
                String horaFinHorario = "" + horaFin + ":" + minutosFin + ":00";

                //actualizar horario
                baseDatosHelper.actualizarHorario(idHorarioEditar, dias, horaInicioHorario, horaFinHorario);


                //actualizar propuesta
                baseDatosHelper.actualizarPropuesta(idPropuesta, "0", "1", idEvento, idCoordinador);

                //actulizar prioridad NO
                //baseDatosHelper.actualizarPrioridad(idPrioridad,"1");

                //actulizar evento
                baseDatosHelper.actualizarEvento(idEvento, idGrupo, idPropuesta, idHorarioEditar, idPrioridad, idLocal, nombreEvento, tipoEvento);

                Toast.makeText(EditarSolicitudesHorarioActivity.this, R.string.solicitud_editada_correctamete, Toast.LENGTH_SHORT).show();
                finish();



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
            Log.e("AgregarAsignacionActivity - Materis", data);
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