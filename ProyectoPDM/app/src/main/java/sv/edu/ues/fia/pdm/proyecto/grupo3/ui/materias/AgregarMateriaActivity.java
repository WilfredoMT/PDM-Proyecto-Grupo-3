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
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.AgregarCicloActivity;

public class AgregarMateriaActivity extends AppCompatActivity {

    BaseDatosHelper baseDatosHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_materia);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.agregar_materia);


        Button buttonAgregar = findViewById(R.id.buttonAgregarMateria);
        Spinner spinnerNumeroCiclo = findViewById(R.id.SpinnerNumeroCiclo);
        EditText nombreTextview = findViewById(R.id.EditTextNombreMateria);
        EditText codigoTextview = findViewById(R.id.EditTextCodigoMateria);
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nombreMateria, codigoMateria, numeroCiclo;

                numeroCiclo = spinnerNumeroCiclo.getSelectedItem().toString();


                nombreMateria = nombreTextview.getText().toString();
                codigoMateria = codigoTextview.getText().toString();

                baseDatosHelper = new BaseDatosHelper(getBaseContext());

                if (!baseDatosHelper.existeAsignatura(codigoMateria)) {
                    // Si no existe agregar materia
                    baseDatosHelper.agregarAsignaturas(nombreMateria, codigoMateria, numeroCiclo);
                    Toast.makeText(AgregarMateriaActivity.this, R.string.materia_agregada_correctamente, Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    // Si existe mostrar error
                    Toast.makeText(AgregarMateriaActivity.this, R.string.la_materia_ya_existe, Toast.LENGTH_SHORT).show();
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