package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;

public class EditarCicloActivity extends AppCompatActivity {

    BaseDatosHelper baseDatosHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ciclo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.editar_ciclo);

        Button buttonEditar = findViewById(R.id.buttonEditarCiclo);
        Spinner spinnerNumeroCiclo = findViewById(R.id.SpinnerNumeroCicloEditar);
        Spinner spinnerAnioCiclo = findViewById(R.id.SpinnerAnioCicloEditar);
        DatePicker datepickerFin = findViewById(R.id.dp_datepickerFinEditar);
        DatePicker datepickerInicio = findViewById(R.id.dp_datepickerInicioEditar);
        TextView textViewEditando = findViewById(R.id.textViewEditandoCiclo);

        //recuperar info de intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("idEditar");


        String nombreCiclo, fechaInicio, fechaFin, idCiclo;



        //recuperar datos
        baseDatosHelper = new BaseDatosHelper(getBaseContext());
        Cursor cursor = baseDatosHelper.getCiclo(id);
        int idIndex = cursor.getColumnIndex(BaseDatosHelper.KEY_idCiclo);
        int nombreIndex = cursor.getColumnIndex(BaseDatosHelper.KEY_nombreCiclo);
        int fechaInicioIndex = cursor.getColumnIndex(BaseDatosHelper.KEY_fechaInicioCiclo);
        int fechaFinalIndex = cursor.getColumnIndex(BaseDatosHelper.KEY_fechaFinCiclo);


        Log.e("EditarCicloActivity", "nombreEditar" + nombreIndex);
        Log.e("EditarCicloActivity", "nombreIndex" + nombreIndex);
        Log.e("EditarCicloActivity", "fechaInicioIndex" + fechaInicioIndex);
        Log.e("EditarCicloActivity", "fechaFinalIndex" + fechaFinalIndex);
        Log.e("EditarCicloActivity", "idIndex" + idIndex);

        fechaInicio = cursor.getString(fechaInicioIndex);
        fechaFin = cursor.getString(fechaFinalIndex);
        idCiclo = id;
        nombreCiclo = cursor.getString(nombreIndex);


        Log.e("EditarCicloActivity", "idCiclo " + idCiclo);
        Log.e("EditarCicloActivity", "nombreCiclo " + nombreCiclo);
        Log.e("EditarCicloActivity", "fechaInicio " + fechaInicio);
        Log.e("EditarCicloActivity", "fechaFin " + fechaFin);


        //Separando Strings
        String[] partesNombre = nombreCiclo.split(" ");
        String numeroCiclo = partesNombre[1];
        Log.i("EditarCicloActivity", "numeroCiclo " + numeroCiclo);
        String anioCiclo = partesNombre[2];
        Log.i("EditarCicloActivity", "anioCiclo " + anioCiclo);

        String[] partesFechaInicio = fechaInicio.split("-");
        String anioCicloFechaInicio = partesFechaInicio[0];
        Log.i("EditarCicloActivity", "anioCicloFechaInicio " + anioCicloFechaInicio);
        String mesCicloFechaInicio = partesFechaInicio[1];
        Log.i("EditarCicloActivity", "mesCicloFechaInicio " + mesCicloFechaInicio);
        String diaCicloFechaInicio = partesFechaInicio[2];
        Log.i("EditarCicloActivity", "diaCicloFechaInicio " + diaCicloFechaInicio);


        String[] partesFechaFin = fechaFin.split("-");
        String anioCicloFechaFin = partesFechaFin[0];
        Log.i("EditarCicloActivity", "anioCicloFechaFin " + anioCicloFechaFin);
        String mesCicloFechaFin = partesFechaFin[1];
        Log.i("EditarCicloActivity", "mesCicloFechaFin " + mesCicloFechaFin);
        String diaCicloFechaFin = partesFechaFin[2];
        Log.i("EditarCicloActivity", "diaCicloFechaFin " + diaCicloFechaFin);

        //LLenando Views

        textViewEditando.setText(getString(R.string.editando_el_ciclos) + nombreCiclo );


        switch (Integer.parseInt(numeroCiclo))
        {
            case 1:
                spinnerNumeroCiclo.setSelection(0);
                break;

            case 2:
                spinnerNumeroCiclo.setSelection(1);
                break;
        }


        switch (Integer.parseInt(anioCiclo))
        {
            case 2022:
                spinnerAnioCiclo.setSelection(0);
                break;
            case 2023:
                spinnerAnioCiclo.setSelection(1);
                break;
            case 2024:
                spinnerAnioCiclo.setSelection(2);
                break;
            case 2025:
                spinnerAnioCiclo.setSelection(3);
                break;
            case 2026:
                spinnerAnioCiclo.setSelection(4);
                break;
        }


        datepickerInicio.updateDate(Integer.parseInt(anioCicloFechaInicio), Integer.parseInt(mesCicloFechaInicio)-1,Integer.parseInt(diaCicloFechaInicio));
        datepickerFin.updateDate(Integer.parseInt(anioCicloFechaFin), Integer.parseInt(mesCicloFechaFin)-1,Integer.parseInt(diaCicloFechaFin));


        buttonEditar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //baseDatosHelper.actualizarCiclo()
                String anioCicloEditado, numeroCicloEditado, fechaInicioEditado, fechaFinEditado ;


                anioCicloEditado = spinnerAnioCiclo.getSelectedItem().toString();
                numeroCicloEditado = spinnerNumeroCiclo.getSelectedItem().toString();


                fechaInicioEditado = formatFecha(datepickerInicio.getYear(), datepickerInicio.getMonth(),  datepickerInicio.getDayOfMonth() );
                fechaFinEditado = formatFecha(datepickerFin.getYear(),  datepickerFin.getMonth() ,datepickerFin.getDayOfMonth() );

                String nombreEditado = "Ciclo " + numeroCicloEditado + " " + anioCicloEditado;



                baseDatosHelper = new BaseDatosHelper(getBaseContext());
                if (!baseDatosHelper.existeCiclo(nombreEditado, id) ) {
                    // Si no existe permitir editar ciclo
                    baseDatosHelper.actualizarCiclo(idCiclo, nombreEditado, fechaInicioEditado.toString(), fechaFinEditado.toString());
                    Toast.makeText(EditarCicloActivity.this, R.string.ciclo_editado_correctamente, Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    // Si existe mostrar error
                    Toast.makeText(EditarCicloActivity.this, R.string.el_ciclo_ya_existe_en_la_base_de_datos, Toast.LENGTH_SHORT).show();
                }


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