package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.asignar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.coordinadores.AgregarCoordinadoresActivity;

public class AgregarAsignacionActivity extends AppCompatActivity {

    BaseDatosHelper baseDatosHelper;
    private ArrayAdapter<String> adapter, adapter2;
    private ArrayList<String> dataListMaterias, dataListCoordinadores ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_asignacion);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.agregar_asignacion_de_coordinador);

        Button buttonAgregar = findViewById(R.id.buttonAgregar);
        Spinner spinnerMateria = findViewById(R.id.SpinnerMateria);
        Spinner spinnnerCoordinador = findViewById(R.id.SpinnerCoordinador);
        EditText editTextIntegrantes = findViewById(R.id.editTextIntegrantes);
        Context context;

        //llenar spinner materia

        dataListMaterias = new ArrayList<>();
        dataListCoordinadores = new ArrayList<>();
        fetchDatosDB();

        // Crear un ArrayAdapter usando el dataList
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataListMaterias);

        //  set el layout que usar en el spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Usar el adapter
        spinnerMateria.setAdapter(adapter);

        //llenar spinner coordinador con datos
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataListCoordinadores);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnnerCoordinador.setAdapter(adapter2);


        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                baseDatosHelper = new BaseDatosHelper(getBaseContext());

                String idCoordinador, idAsignatura, totalIntegrantes, nombreCoord, codigoMateria;

                nombreCoord = spinnnerCoordinador.getSelectedItem().toString();
                codigoMateria = spinnerMateria.getSelectedItem().toString();
                totalIntegrantes = editTextIntegrantes.getText().toString();

                //Conseguir id de opciones
                Cursor cursor = baseDatosHelper.getCoordinador(nombreCoord, null);
                int idCoordIndex = cursor.getColumnIndex(BaseDatosHelper.KEY_idCoordinador);
                idCoordinador = cursor.getString(idCoordIndex);

                cursor = baseDatosHelper.getAsignatura(codigoMateria, null);
                int idMateriaIndex = cursor.getColumnIndex(BaseDatosHelper.KEY_idAsignatura);
                idAsignatura = cursor.getString(idMateriaIndex);


                //crear grupo

                baseDatosHelper.agregarGrupoAsignatura(idAsignatura, idCoordinador, totalIntegrantes);
                Toast.makeText(AgregarAsignacionActivity.this, "Materia asignada correctamente", Toast.LENGTH_SHORT).show();
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
                "codigo" // Replace "columnName" with the actual column name from your database
        };
        String[] projection2 = {
                BaseDatosHelper.KEY_nombreCoordinador // Replace "columnName" with the actual column name from your database
        };

        // Perform a query on the database
        Cursor cursor = db.query(
                "asignatura",   // The table name
                projection,             // The columns to return
                null,          // The columns for the WHERE clause
                null,      // The values for the WHERE clause
                null,          // don't group the rows
                null,           // don't filter by row groups
                null            // The sort order
        );

        Cursor cursor2 = db.query(
                "docenteCoordinador",   // The table name
                projection2,             // The columns to return
                null,          // The columns for the WHERE clause
                null,      // The values for the WHERE clause
                null,          // don't group the rows
                null,           // don't filter by row groups
                null            // The sort order
        );

        // Iterate through the cursor and populate dataList
        while (cursor.moveToNext()) {
            String data = cursor.getString(cursor.getColumnIndexOrThrow("codigo"));
            Log.e("AgregarAsignacionActivity - Materis" , data);
            dataListMaterias.add(data);
        }

        while (cursor2.moveToNext()) {
            String data2 = cursor2.getString(cursor2.getColumnIndexOrThrow("nombre"));
            Log.e("AgregarAsignacionActivity - Coords", data2);
            dataListCoordinadores.add(data2);
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