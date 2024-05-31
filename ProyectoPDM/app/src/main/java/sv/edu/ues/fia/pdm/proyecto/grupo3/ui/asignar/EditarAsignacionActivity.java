package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.asignar;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales.EditarLocalesActivity;

public class EditarAsignacionActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter, adapter2;
    private ArrayList<String> dataListMaterias, dataListCoordinadores ;

    BaseDatosHelper baseDatosHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_asignacion);

        //recuperar info de intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String nombre = intent.getStringExtra("nombre");
        String codigo = intent.getStringExtra("materia");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.editar_asignacion);

        Button buttonEditar = findViewById(R.id.buttonEditar);
        Spinner spinnerMateria = findViewById(R.id.SpinnerMateria);
        Spinner spinnnerCoordinador = findViewById(R.id.SpinnerCoordinador);
        EditText editTextIntegrantes = findViewById(R.id.editTextIntegrantes);
        TextView textView = findViewById(R.id.textView5);


        //recuperar datos
        baseDatosHelper = new BaseDatosHelper(getBaseContext());
        Cursor cursor = baseDatosHelper.getGrupoAsignatura(id);
        String totalIntegrantes = cursor.getString(3);


        //llenar views
        textView.setText(getString(R.string.editandoencargado) + " " + nombre);
        editTextIntegrantes.setText(totalIntegrantes);

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

        //Setear spinners con seleccion correcta
        if (adapter != null) {
            // Iterar hasta encontrar la posicion del codigomateria
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).equals(codigo)) {
                    // Set la seleccion
                    spinnerMateria.setSelection(i);
                    break;
                }
            }
        }
        if (adapter2 != null) {
            // Iterar hasta encontrar la posicion del nombreCoord
            for (int i = 0; i < adapter2.getCount(); i++) {
                if (adapter2.getItem(i).equals(nombre)) {
                    // Set la seleccion
                    spinnnerCoordinador.setSelection(i);
                    break;
                }
            }
        }

        buttonEditar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String nombreCoordinadorEditado, codigoMateriaEditado, totalIntegrantesEditado;
                final String[] idCoordinador = new String[1];
                final String[] idAsignatura = new String[1];

                nombreCoordinadorEditado = spinnnerCoordinador.getSelectedItem().toString();
                codigoMateriaEditado = spinnerMateria.getSelectedItem().toString();
                totalIntegrantesEditado = editTextIntegrantes.getText().toString();

                baseDatosHelper.getAsignatura(codigoMateriaEditado, new BaseDatosHelper.Callback() {
                    @Override
                    public boolean onSuccess(Cursor cursor) {
                        idAsignatura[0] = cursor.getString(0);

                        cursor = baseDatosHelper.getCoordinador(nombreCoordinadorEditado, null);
                        idCoordinador[0] = cursor.getString(0);
                        return false;
                    }

                    @Override
                    public void onError(String error) {

                    }
                });




                baseDatosHelper.actualizarGrupoAsignatura(id, idAsignatura[0], idCoordinador[0], totalIntegrantesEditado);
                Toast.makeText(EditarAsignacionActivity.this, R.string.grupo_editado_correctamente, Toast.LENGTH_SHORT).show();
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

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}