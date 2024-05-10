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

        String nombremateria, codigoMateria, numeroCiclo;

        //recuperar datos
        baseDatosHelper = new BaseDatosHelper(getBaseContext());
        Cursor cursor = baseDatosHelper.getAsignatura(id);

        nombremateria = cursor.getString(1);
        codigoMateria = cursor.getString(2);
        numeroCiclo = cursor.getString(3);

        //LLenando Views

        textViewEditando.setText(getString(R.string.editando_materia ) + " "+nombremateria);

        switch (Integer.parseInt(numeroCiclo))
        {
            case 1:
                spinnerNumeroCiclo.setSelection(0);
                break;

            case 2:
                spinnerNumeroCiclo.setSelection(1);
                break;
        }

        editTextNombre.setText(nombremateria);
        editTextCodigo.setText(codigoMateria);


        buttonEditar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String nombreMateriaEditado, numeroCicloEditado, codigoMateriaEditado;

                numeroCicloEditado = spinnerNumeroCiclo.getSelectedItem().toString();
                nombreMateriaEditado = editTextNombre.getText().toString();
                codigoMateriaEditado = editTextCodigo.getText().toString();


                baseDatosHelper = new BaseDatosHelper(getBaseContext());
                if (!baseDatosHelper.existeAsignatura(nombreMateriaEditado, codigoMateriaEditado, id) ) {
                    // Si no existe permitir editar ciclo
                    baseDatosHelper.actualizarAsignatura(id, nombreMateriaEditado, codigoMateriaEditado, numeroCicloEditado);
                    Toast.makeText(EditarMateriaActivity.this, R.string.materia_editada_correctamente, Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    // Si existe mostrar error
                    Toast.makeText(EditarMateriaActivity.this, R.string.la_materia_ya_existe, Toast.LENGTH_SHORT).show();
                }


            }
        });





    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}