package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;

public class AgregarLocalesActivity extends AppCompatActivity {
    BaseDatosHelper baseDatosHelper;
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



        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nombreLocal, tipoLocal, capacidad;

                tipoLocal = spinnerTipoLocal.getSelectedItem().toString();
                nombreLocal = editTextNombre.getText().toString();
                capacidad = editTextapacidad.getText().toString();


                baseDatosHelper = new BaseDatosHelper(getBaseContext());
                if (!baseDatosHelper.existeLocal(nombreLocal)) {
                    // Si no existe agregar ciclo
                    baseDatosHelper.agregarLocal(nombreLocal, tipoLocal, capacidad);
                    Toast.makeText(AgregarLocalesActivity.this, R.string.local_agregado_correctamente, Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    // Si existe mostrar error
                    Toast.makeText(AgregarLocalesActivity.this, R.string.el_local_ya_existe_en_la_base_de_datos, Toast.LENGTH_SHORT).show();
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

