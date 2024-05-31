package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.materias.AgregarMateriaActivity;

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
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerEscuela.setAdapter(adapter);

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
                final String[] idEscuela = new String[1];

                String nombreEscuela = SpinnerEscuela.getSelectedItem().toString();
                baseDatosHelper.getEscuela(nombreEscuela, new BaseDatosHelper.Callback() {
                    @Override
                    public boolean onSuccess(Cursor cursor) {
                        cursor.moveToFirst();
                        int idIndex = cursor.getColumnIndex(BaseDatosHelper.KEY_idEscuela);
                        idEscuela[0] = cursor.getString(idIndex);
                        return false;
                    }

                    @Override
                    public void onError(String error) {

                    }
                });



                String nombreLocal, tipoLocal, capacidad;

                tipoLocal = spinnerTipoLocal.getSelectedItem().toString();
                nombreLocal = editTextNombre.getText().toString();
                capacidad = editTextapacidad.getText().toString();


                baseDatosHelper = new BaseDatosHelper(getBaseContext());
                baseDatosHelper.existeLocal(nombreLocal, new BaseDatosHelper.ExisteCallback() {
                    @Override
                    public void onResult(boolean existe) {
                        if (existe) {
                            // Si existe mostrar error
                            Toast.makeText(AgregarLocalesActivity.this, R.string.el_local_ya_existe_en_la_base_de_datos, Toast.LENGTH_SHORT).show();



                        } else {
                            // Si no existe agregar local
                            baseDatosHelper.agregarLocal(nombreLocal, tipoLocal, capacidad, idEscuela[0]);
                            Toast.makeText(AgregarLocalesActivity.this, R.string.local_agregado_correctamente, Toast.LENGTH_SHORT).show();
                            finish();


                        }

                    }
                });


            }
        });

    }
    private void fetchDatosDB() {
        // Assuming you have a SQLiteOpenHelper subclass named MyDbHelper
        baseDatosHelper = new BaseDatosHelper(getBaseContext());

        baseDatosHelper.getEscuelas(new BaseDatosHelper.Callback() {
            @Override
            public boolean onSuccess(Cursor cursor) {
                // Iterate through the cursor and populate dataList
                while (cursor.moveToNext()) {
                    String data = cursor.getString(cursor.getColumnIndexOrThrow("nomEscuela"));
                    Log.e("Escuelas", data);
                    dataList.add(data);
                }

                // Close the cursor and database
                cursor.close();
                baseDatosHelper.close();
                //notificar adapter
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public void onError(String error) {

            }
        });




    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

