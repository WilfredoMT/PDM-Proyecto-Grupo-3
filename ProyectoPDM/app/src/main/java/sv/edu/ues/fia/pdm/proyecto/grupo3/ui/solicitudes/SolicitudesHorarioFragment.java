package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.LoginActivity;
import sv.edu.ues.fia.pdm.proyecto.grupo3.MainActivity;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import android.util.Log;
import com.google.android.material.snackbar.Snackbar;
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentSolicitudeshorarioBinding;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.AgregarCicloActivity;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.CicloAdapter;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.CicloFragment;

import android.os.Handler;
import android.os.Looper;

public class SolicitudesHorarioFragment extends Fragment {

    private FragmentSolicitudeshorarioBinding binding;
    private BaseDatosHelper baseDatosHelper;
    private RecyclerView mRecyclerView;
    private SolicitudesHorarioAdapter mAdapter;
    private BaseDatosHelper mDbHelper;

    private static SolicitudesHorarioFragment instance;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes.SolicitudesHorarioViewModel solicitudesHorarioViewModel =
                new ViewModelProvider(this).get(sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes.SolicitudesHorarioViewModel.class);

        instance = this;
        MainActivity act =(MainActivity) getActivity();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Solicitudes de horario");
        }

        binding = FragmentSolicitudeshorarioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Datos iniciales
        baseDatosHelper = new BaseDatosHelper(getContext());
        baseDatosHelper.insertarDatosInicialesEvento();
        baseDatosHelper.insertarDatosInicialesPropuesta();
        baseDatosHelper.insertarDatosInicialesHorario();
        baseDatosHelper.insertarDatosInicialesPrioridad();




        String usuariooordinador = act.infoUsuario[0];
        Cursor cursor = baseDatosHelper.getUsuario(usuariooordinador);
        String idUsuario = cursor.getString(0);
        String nombreCoordindo = cursor.getString(1);
        cursor = baseDatosHelper.getCoordinador(idUsuario,null, null);
        String idCoordinador = cursor.getString(0);


        final TextView textView = binding.textSolicitudhorario;

        solicitudesHorarioViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        solicitudesHorarioViewModel.getText().observe(getViewLifecycleOwner(), newText -> {
            // texto para la texview
            textView.setText(getString(R.string.solicitudes_de_horario) +": " + nombreCoordindo);
        });

        //Llenar RecyclerView
        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SolicitudesHorarioAdapter(getContext(), null, nombreCoordindo);
        mRecyclerView.setAdapter(mAdapter);

        mDbHelper = new BaseDatosHelper(getContext());
        cursor = mDbHelper.getReadableDatabase().query(
                BaseDatosHelper.EVENTO_TABLA,
                null,
                null,
                null,
                null,
                null,
                null
        );
        mAdapter.swapCursor(cursor);
        //Acciones FAB
        //FaB visible
        ((MainActivity) requireActivity()).binding.appBarMain.fab.setVisibility(View.VISIBLE);

        ((MainActivity) requireActivity()).setFabIcon(ContextCompat.getDrawable(requireContext(), R.drawable.plus));

        // click listener para este fragment
        ((MainActivity) requireActivity()).setFabClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Action specific to Fragment 1

                // on below line creating a child fragment

                Intent intent = new Intent(getActivity(), AgregarSolicitudesHorarioActivity.class);
                intent.putExtra("nombreLogueadoCoordinador", nombreCoordindo);
                intent.putExtra("idUsuarioLoguado", idUsuario);
                intent.putExtra("idCoordinadorLoguado", idCoordinador);
                startActivity(intent);


                /*
                Snackbar.make(view, "Action from Fragment 1", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                 */
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) requireActivity()).binding.appBarMain.fab.setVisibility(View.GONE);
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerView();
    }

    //Instancia para abrir en otros class/frgamnts/activities
    public static SolicitudesHorarioFragment getInstance() {
        return instance;
    }

    public void refreshRecyclerView() {
        ((MainActivity) requireActivity()).binding.appBarMain.fab.setVisibility(View.VISIBLE);
        Cursor cursor = mDbHelper.getReadableDatabase().query(
                BaseDatosHelper.EVENTO_TABLA,
                null,
                null,
                null,
                null,
                null,
                null
        );
        mAdapter.swapCursor(cursor);
    }
}