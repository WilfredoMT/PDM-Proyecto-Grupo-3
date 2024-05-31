package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.materias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.EditarCicloActivity;

public class EditarMateriaActivity extends AppCompatActivity {

    BaseDatosHelper baseDatosHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_materia);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.editar_materia);


        TextView textViewEditando = findViewById(R.id.textView);
        Button buttonEditar = findViewById(R.id.buttonEditarMateria);
        EditText editTextNombre = findViewById(R.id.EditTextEditarNombreMateria);
        EditText editTextCodigo = findViewById(R.id.EditTextEditarCodigoMateria);
        Spinner spinnerNumeroCiclo = findViewById(R.id.SpinnerEditarNumeroCicloEditar);

        //recuperar info de intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        final String[] nombremateria = new String[1];
        final String[] codigoMateria = new String[1];
        final String[] numeroCiclo = new String[1];

        //recuperar datos
        baseDatosHelper = new BaseDatosHelper(getBaseContext());
        baseDatosHelper.getAsignatura(id, new BaseDatosHelper.Callback() {
            @Override
            public boolean onSuccess(Cursor cursor) {

                cursor.moveToFirst();
                nombremateria[0] = cursor.getString(1);
                codigoMateria[0] = cursor.getString(3);
                numeroCiclo[0] = cursor.getString(2);

                //LLenando Views

                textViewEditando.setText(getString(R.string.editando_materia ) + " "+ nombremateria[0]);

                switch (Integer.parseInt(numeroCiclo[0]))
                {
                    case 1:
                        spinnerNumeroCiclo.setSelection(0);
                        break;

                    case 2:
                        spinnerNumeroCiclo.setSelection(1);
                        break;
                }

                editTextNombre.setText(nombremateria[0]);
                editTextCodigo.setText(codigoMateria[0]);
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


                String nombreMateriaEditado, numeroCicloEditado, codigoMateriaEditado;

                numeroCicloEditado = spinnerNumeroCiclo.getSelectedItem().toString();
                nombreMateriaEditado = editTextNombre.getText().toString();
                codigoMateriaEditado = editTextCodigo.getText().toString();


                baseDatosHelper = new BaseDatosHelper(getBaseContext());
                baseDatosHelper.existeAsignatura(codigoMateriaEditado, id, new BaseDatosHelper.ExisteCallback() {
                    @Override
                    public void onResult(boolean existe) {
                        if (existe) {
                            // Si existe mostrar error
                            Toast.makeText(EditarMateriaActivity.this, R.string.la_materia_ya_existe, Toast.LENGTH_SHORT).show();


                        } else {
                            // Si no existe permitir editar materia
                            baseDatosHelper.actualizarAsignatura(id, nombreMateriaEditado, numeroCicloEditado, codigoMateriaEditado);
                            Toast.makeText(EditarMateriaActivity.this, R.string.materia_editada_correctamente, Toast.LENGTH_SHORT).show();
                            finish();


                        }

                    }
                });



            }
        });





    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}