package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.horarios;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentHorariosBinding;

public class HorariosFragment extends Fragment {
    private BaseDatosHelper dbHelper;
    private Context mContext;

    private FragmentHorariosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sv.edu.ues.fia.pdm.proyecto.grupo3.ui.horarios.HorariosViewModel horariosViewModel =
                new ViewModelProvider(this).get(sv.edu.ues.fia.pdm.proyecto.grupo3.ui.horarios.HorariosViewModel.class);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Horarios");
        }

        binding = FragmentHorariosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHelper = new BaseDatosHelper(requireContext()); // Use requireContext() or getActivity() based on your requirements

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseDatosHelper.EVENTO_TABLA + "." + BaseDatosHelper.KEY_nomEvento, // Include table name for disambiguation
                BaseDatosHelper.HORARIO_TABLA + "." + BaseDatosHelper.KEY_diaHorario,
                BaseDatosHelper.HORARIO_TABLA + "." + BaseDatosHelper.KEY_horaInicioHorario,
                BaseDatosHelper.HORARIO_TABLA + "." + BaseDatosHelper.KEY_horaFinHorario
        };

// Define the selection criteria
        String selection = BaseDatosHelper.EVENTO_TABLA + "." + BaseDatosHelper.KEY_idPrioridadEvento + " = ?"; // Adjust the column name based on your schema
        String[] selectionArgs = { "2" };

// Perform the query by joining the tables
        Cursor cursor = db.query(
                BaseDatosHelper.EVENTO_TABLA + " INNER JOIN " + BaseDatosHelper.HORARIO_TABLA +
                        " ON " + BaseDatosHelper.EVENTO_TABLA + "." + BaseDatosHelper.KEY_idHorarioEvento +
                        " = " + BaseDatosHelper.HORARIO_TABLA + "." + BaseDatosHelper.KEY_idHorario,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String eventName = cursor.getString(cursor.getColumnIndex(BaseDatosHelper.KEY_nomEvento));
            @SuppressLint("Range") String dayOfWeek = cursor.getString(cursor.getColumnIndex(BaseDatosHelper.KEY_diaHorario));
            @SuppressLint("Range") String startTime = cursor.getString(cursor.getColumnIndex(BaseDatosHelper.KEY_horaInicioHorario));
            @SuppressLint("Range") String endTime = cursor.getString(cursor.getColumnIndex(BaseDatosHelper.KEY_horaFinHorario));

            // Append the event details to the TextView

            Log.e("Horario", getString(R.string.diasemana) + dayOfWeek + "\n");
            scheduleTextView.append(getString(R.string.nombreEvento) + eventName + "\n");
            scheduleTextView.append(getString(R.string.diasemana) + dayOfWeek + "\n");
            scheduleTextView.append(getString(R.string.horainicios) + startTime + "\n");
            scheduleTextView.append(getString(R.string.horaFIn) + endTime + "\n\n");
        }






        final TextView textView = binding.scheduleTextView;
        horariosViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}