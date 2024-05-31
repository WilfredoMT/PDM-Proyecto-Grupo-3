package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private Spinner SpinnerEscuela;

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
        SpinnerEscuela = findViewById(R.id.spinnerEscuela);

        // inicializar adapter y datalist
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerEscuela.setAdapter(adapter);

        //recuperar info de intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("idEditar");
        Log.i("EditarLocalesActivity", id);

        final String[] nombreLocal = new String[1];
        final String[] capacidadLocal = new String[1];
        final String[] tipoLocal = new String[1];
        final String[] idEscuela = new String[1];

        //recuperar datos
        baseDatosHelper = new BaseDatosHelper(getBaseContext());
        baseDatosHelper.getLocal(id, new BaseDatosHelper.Callback() {
            @Override
            public boolean onSuccess(Cursor cursor) {
                cursor.moveToFirst();
                nombreLocal[0] = cursor.getString(1);
                tipoLocal[0] = cursor.getString(2);
                capacidadLocal[0] = cursor.getString(3);
                idEscuela[0] = cursor.getString(4);



                //LLenando Views
                textViewEditando.setText(getString(R.string.editar_local) + ": " + nombreLocal[0]);

                baseDatosHelper = new BaseDatosHelper(getBaseContext());




                Context context = getBaseContext();
                Resources resources = context.getResources();

                String Auditorio = resources.getStringArray(R.array.tipoLocales)[0];
                String Biblioteca = resources.getStringArray(R.array.tipoLocales)[1];
                String Salon = resources.getStringArray(R.array.tipoLocales)[2];
                String Edificio = resources.getStringArray(R.array.tipoLocales)[3];
                String Otro = resources.getStringArray(R.array.tipoLocales)[4];

                if (tipoLocal[0].equals(Auditorio)) {
                    spinnerTipo.setSelection(0);
                } else if (tipoLocal[0].equals(Biblioteca)) {
                    spinnerTipo.setSelection(1);
                } else if (tipoLocal[0].equals(Salon)) {
                    spinnerTipo.setSelection(2);


                } else if (tipoLocal[0].equals(Edificio)) {
                    spinnerTipo.setSelection(3);

                } else if (tipoLocal[0].equals(Otro)) {
                    spinnerTipo.setSelection(4);

                }


                editTextNombre.setText(nombreLocal[0]);
                editTextCapacidad.setText(capacidadLocal[0]);

                fetchDatosDB(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter != null) {

                            //Info de escuela
                            final String[] nombreEscuela = new String[1];
                            baseDatosHelper.getEscuela(idEscuela[0], new BaseDatosHelper.Callback() {
                                @Override
                                public boolean onSuccess(Cursor cursor) {
                                    cursor.moveToFirst();
                                    nombreEscuela[0] = cursor.getString(1);

                                    Log.e("nombreEscuela", nombreEscuela[0]);
                                    // Iterar hasta encontrar la posicion del nombreEscuela
                                    for (int i = 0; i < adapter.getCount(); i++) {
                                        if (adapter.getItem(i).equals(nombreEscuela[0])) {
                                            // Set la seleccion
                                            SpinnerEscuela.setSelection(i);
                                            break;
                                        }
                                    }
                                    return false;
                                }

                                @Override
                                public void onError(String error) {

                                }
                            }, null);

                        }

                    }
                });
                return false;
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getBaseContext(), "Error: " + error, Toast.LENGTH_SHORT).show();

            }
        });





        buttonEditar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String nombreLocalEditado;
                String capacidadEditado;
                String tipoEditado;
                final String[] idEscuelaEditado = new String[1];

                tipoEditado = spinnerTipo.getSelectedItem().toString();
                nombreLocalEditado = editTextNombre.getText().toString();
                capacidadEditado = editTextCapacidad.getText().toString();

                String nombreEscuelaEditado = SpinnerEscuela.getSelectedItem().toString();
                baseDatosHelper.getEscuela(nombreEscuelaEditado, new BaseDatosHelper.Callback() {
                    @Override
                    public boolean onSuccess(Cursor cursor) {
                        cursor.moveToFirst();
                        int idIndex = cursor.getColumnIndex(BaseDatosHelper.KEY_idEscuela);
                        idEscuelaEditado[0] = cursor.getString(idIndex);
                        return false;
                    }

                    @Override
                    public void onError(String error) {

                    }
                });



                baseDatosHelper = new BaseDatosHelper(getBaseContext());
                baseDatosHelper.existeLocal(nombreLocalEditado, id, new BaseDatosHelper.ExisteCallback() {
                    @Override
                    public void onResult(boolean existe) {
                        if (existe) {
                            // Si existe mostrar error
                            Toast.makeText(EditarLocalesActivity.this, R.string.el_local_ya_existe_en_la_base_de_datos, Toast.LENGTH_SHORT).show();


                        } else {
                            // Si no existe permitir editar local
                            baseDatosHelper.actualizarLocal(id, nombreLocalEditado, tipoEditado, capacidadEditado, idEscuelaEditado[0]);
                            Toast.makeText(EditarLocalesActivity.this, R.string.local_editado_correctamente, Toast.LENGTH_SHORT).show();
                            finish();



                        }

                    }
                });



            }
        });


    }

    private void fetchDatosDB( Runnable callback ) {
        // Assuming you have a SQLiteOpenHelper subclass named MyDbHelper
        baseDatosHelper = new BaseDatosHelper(getBaseContext());

        baseDatosHelper.getEscuelas(new BaseDatosHelper.Callback() {
            @Override
            public boolean onSuccess(Cursor cursor) {
                // Iterate through the cursor and populate dataList
                while (cursor.moveToNext()) {
                    String data = cursor.getString(cursor.getColumnIndexOrThrow("nomEscuela"));
                    dataList.add(data);
                }

                // Close the cursor and database
                cursor.close();
                baseDatosHelper.close();
                //notificar adapter
                adapter.notifyDataSetChanged();

                // Execute callback
                if (callback != null) {
                    callback.run();
                }


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
