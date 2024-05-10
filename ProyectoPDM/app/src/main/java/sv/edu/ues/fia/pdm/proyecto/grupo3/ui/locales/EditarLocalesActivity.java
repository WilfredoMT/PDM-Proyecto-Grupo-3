package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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


public class EditarLocalesActivity extends AppCompatActivity {

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

        //recuperar info de intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("idEditar");
        Log.i("EditarLocalesActivity", id);

        String nombreLocal, capacidadLocal, tipoLocal;

        //recuperar datos
        baseDatosHelper = new BaseDatosHelper(getBaseContext());
        Cursor cursor = baseDatosHelper.getLocal(id);

        nombreLocal = cursor.getString(1);
        tipoLocal = cursor.getString(2);
        capacidadLocal = cursor.getString(3);

        //LLenando Views

        textViewEditando.setText(getString(R.string.editar_local) + ": " + nombreLocal);


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


                String nombreLocalEditado, capacidadEditado, tipoEditado;

                tipoEditado = spinnerTipo.getSelectedItem().toString();
                nombreLocalEditado = editTextNombre.getText().toString();
                capacidadEditado = editTextCapacidad.getText().toString();


                baseDatosHelper = new BaseDatosHelper(getBaseContext());
                if (!baseDatosHelper.existeLocal(nombreLocalEditado, id)) {
                    // Si no existe permitir editar local
                    baseDatosHelper.actualizarLocal(id, nombreLocalEditado, tipoEditado, capacidadEditado);
                    Toast.makeText(EditarLocalesActivity.this, R.string.local_editado_correctamente, Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    // Si existe mostrar error
                    Toast.makeText(EditarLocalesActivity.this, R.string.el_local_ya_existe_en_la_base_de_datos, Toast.LENGTH_SHORT).show();
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
