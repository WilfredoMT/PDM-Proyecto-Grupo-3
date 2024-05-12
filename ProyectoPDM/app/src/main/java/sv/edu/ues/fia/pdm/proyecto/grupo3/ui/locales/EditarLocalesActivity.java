package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.AbstractCursor;
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
import android.util.Log;

import java.util.ArrayList;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;


public class EditarLocalesActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataList;
    BaseDatosHelper baseDatosHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_locales);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.editar_local);


        TextView textViewEditando = findViewById(R.id.textView);
        Button buttonEditar = findViewById(R.id.buttonAgregarLocal);
        EditText editTextNombre = findViewById(R.id.EditTextNombreLocal);
        EditText editTextCapacidad = findViewById(R.id.EditTextCapacidadLocales);
        Spinner spinnerTipo = findViewById(R.id.SpinnerTipoLocales);
        Spinner SpinnerEscuela = findViewById(R.id.spinnerEscuela);

        //recuperar info de intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("idEditar");
        Log.i("EditarLocalesActivity", id);

        String nombreLocal, capacidadLocal, tipoLocal, idEscuela;

        //recuperar datos
        baseDatosHelper = new BaseDatosHelper(getBaseContext());
        Cursor cursor = baseDatosHelper.getLocal(id);

        nombreLocal = cursor.getString(1);
        tipoLocal = cursor.getString(2);
        capacidadLocal = cursor.getString(3);
        idEscuela = cursor.getString(4);

        //Info de escuela
        cursor = baseDatosHelper.getEscuela(idEscuela, null);
        String nombreEscuela = cursor.getString(1);

        //LLenando Views
        textViewEditando.setText(getString(R.string.editar_local) + ": " + nombreLocal);

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

        if (adapter != null) {
            // Iterar hasta encontrar la posicion del nombreEscuela
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).equals(nombreEscuela)) {
                    // Set la seleccion
                    SpinnerEscuela.setSelection(i);
                    break;
                }
            }
        }

        Context context = this;
        Resources resources = context.getResources();

        String Auditorio = resources.getStringArray(R.array.tipoLocales)[0];
        String Biblioteca = resources.getStringArray(R.array.tipoLocales)[1];
        String Salon = resources.getStringArray(R.array.tipoLocales)[2];
        String Edificio = resources.getStringArray(R.array.tipoLocales)[3];
        String Otro = resources.getStringArray(R.array.tipoLocales)[4];

        if (tipoLocal.equals(Auditorio)) {
            spinnerTipo.setSelection(0);
        } else if (tipoLocal.equals(Biblioteca)) {
            spinnerTipo.setSelection(1);
        } else if (tipoLocal.equals(Salon)) {
            spinnerTipo.setSelection(2);


        } else if (tipoLocal.equals(Edificio)) {
            spinnerTipo.setSelection(3);

        } else if (tipoLocal.equals(Otro)) {
            spinnerTipo.setSelection(4);

        }


        editTextNombre.setText(nombreLocal);
        editTextCapacidad.setText(capacidadLocal);


        buttonEditar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String nombreLocalEditado, capacidadEditado, tipoEditado, idEscuelaEditado;

                tipoEditado = spinnerTipo.getSelectedItem().toString();
                nombreLocalEditado = editTextNombre.getText().toString();
                capacidadEditado = editTextCapacidad.getText().toString();

                String nombreEscuelaEditado = SpinnerEscuela.getSelectedItem().toString();
                Cursor cursor = baseDatosHelper.getEscuela(nombreEscuelaEditado);
                int idIndex = cursor.getColumnIndex(BaseDatosHelper.KEY_idEscuela);
                idEscuelaEditado = cursor.getString(idIndex);


                baseDatosHelper = new BaseDatosHelper(getBaseContext());
                if (!baseDatosHelper.existeLocal(nombreLocalEditado, id)) {
                    // Si no existe permitir editar local
                    baseDatosHelper.actualizarLocal(id, nombreLocalEditado, tipoEditado, capacidadEditado, idEscuelaEditado);
                    Toast.makeText(EditarLocalesActivity.this, R.string.local_editado_correctamente, Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    // Si existe mostrar error
                    Toast.makeText(EditarLocalesActivity.this, R.string.el_local_ya_existe_en_la_base_de_datos, Toast.LENGTH_SHORT).show();
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
