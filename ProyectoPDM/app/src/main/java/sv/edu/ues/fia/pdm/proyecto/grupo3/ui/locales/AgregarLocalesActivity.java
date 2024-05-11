package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;

public class AgregarLocalesActivity extends AppCompatActivity {
    BaseDatosHelper baseDatosHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_locales);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.agregar_local);


        Button buttonAgregar = findViewById(R.id.buttonAgregarLocal);
        Spinner spinnerTipoLocal = findViewById(R.id.SpinnerTipoLocales);
        EditText editTextNombre = findViewById(R.id.EditTextNombreLocal);
        EditText editTextapacidad = findViewById(R.id.EditTextCapacidadLocales);
        Spinner SpinnerEscuela = findViewById(R.id.spinnerEscuela);

        //llenar spinner escuela con datos de la tabla escuela
        baseDatosHelper = new BaseDatosHelper(getBaseContext());
        dataList = new ArrayList<>();
        fetchDatosDB();
        // Crear un ArrayAdapter usando el dataList
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataList);

        //  set el layout que usar en el spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Usar el adapter
        SpinnerEscuela.setAdapter(adapter);

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idEscuela;

                String nombreEscuela = SpinnerEscuela.getSelectedItem().toString();
                Cursor cursor = baseDatosHelper.getEscuela(nombreEscuela);
                int idIndex = cursor.getColumnIndex(BaseDatosHelper.KEY_idEscuela);
                idEscuela = cursor.getString(idIndex);


                String nombreLocal, tipoLocal, capacidad;

                tipoLocal = spinnerTipoLocal.getSelectedItem().toString();
                nombreLocal = editTextNombre.getText().toString();
                capacidad = editTextapacidad.getText().toString();


                baseDatosHelper = new BaseDatosHelper(getBaseContext());
                if (!baseDatosHelper.existeLocal(nombreLocal)) {
                    // Si no existe agregar ciclo
                    baseDatosHelper.agregarLocal(nombreLocal, tipoLocal, capacidad, idEscuela);
                    Toast.makeText(AgregarLocalesActivity.this, R.string.local_agregado_correctamente, Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    // Si existe mostrar error
                    Toast.makeText(AgregarLocalesActivity.this, R.string.el_local_ya_existe_en_la_base_de_datos, Toast.LENGTH_SHORT).show();
                }

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
                "nomEscuela" // Replace "columnName" with the actual column name from your database
        };

        // Perform a query on the database
        Cursor cursor = db.query(
                "escuela",   // The table name
                projection,             // The columns to return
                null,          // The columns for the WHERE clause
                null,      // The values for the WHERE clause
                null,          // don't group the rows
                null,           // don't filter by row groups
                null            // The sort order
        );

        // Iterate through the cursor and populate dataList
        while (cursor.moveToNext()) {
            String data = cursor.getString(cursor.getColumnIndexOrThrow("nomEscuela"));
            dataList.add(data);
        }

        // Close the cursor and database
        cursor.close();
        baseDatosHelper.close();
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

