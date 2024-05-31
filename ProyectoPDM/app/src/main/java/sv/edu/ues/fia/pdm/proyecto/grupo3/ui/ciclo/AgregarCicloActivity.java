package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;

public class AgregarCicloActivity extends AppCompatActivity {
    BaseDatosHelper baseDatosHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_ciclo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.agregar_ciclo);


         Button buttonAgregar = findViewById(R.id.buttonAgregarCiclo);
         Spinner spinnerNumeroCiclo = findViewById(R.id.SpinnerNumeroCiclo);
         Spinner spinnerAnioCiclo = findViewById(R.id.SpinnerAnioCiclo);
         DatePicker datepickerFin = findViewById(R.id.dp_datepickerFin);
         DatePicker datepickerInicio = findViewById(R.id.dp_datepickerInicio);


        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String anioCiclo, numeroCiclo;
                String fechaInicio, fechaFin;

                anioCiclo = spinnerAnioCiclo.getSelectedItem().toString();
                numeroCiclo = spinnerNumeroCiclo.getSelectedItem().toString();


                fechaInicio = formatFecha(datepickerInicio.getYear(), datepickerInicio.getMonth(),  datepickerInicio.getDayOfMonth() );
                fechaFin = formatFecha(datepickerFin.getYear(),  datepickerFin.getMonth() ,datepickerFin.getDayOfMonth() );

                String nombre = "Ciclo " + numeroCiclo + " " + anioCiclo;


                baseDatosHelper = new BaseDatosHelper(getBaseContext());
                baseDatosHelper.existeCiclo(nombre, new BaseDatosHelper.ExisteCallback() {
                    @Override
                    public void onResult(boolean existe) {
                        if (existe) {
                            // Si existe mostrar error
                            Toast.makeText(AgregarCicloActivity.this, R.string.el_ciclo_ya_existe_en_la_base_de_datos, Toast.LENGTH_SHORT).show();


                        } else {
                            // Si no existe agregar ciclo
                            baseDatosHelper.agregarCiclo(nombre, fechaInicio, fechaFin);
                            Toast.makeText(AgregarCicloActivity.this, R.string.ciclo_agregado_correctamente, Toast.LENGTH_SHORT).show();
                            finish();

                        }

                    }
                });


            }
        });
    }

    String formatFecha(int anio, int mes, int dia){
        mes = mes+1;
        String formattedMonth = (mes < 10) ? "0" + mes : String.valueOf(mes);
        String formattedDay = (dia < 10) ? "0" + dia : String.valueOf(dia);

        return anio + "-" + formattedMonth + "-" + formattedDay;

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

