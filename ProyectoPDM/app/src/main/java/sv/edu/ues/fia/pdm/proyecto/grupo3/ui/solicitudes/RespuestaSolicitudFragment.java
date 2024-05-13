package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.MainActivity;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentRespuestaSolicitudBinding;
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentSolicitudeshorarioBinding;

public class RespuestaSolicitudFragment extends Fragment {

    private RespuestaSolicitudViewModel mViewModel;
    private FragmentRespuestaSolicitudBinding binding;
    private BaseDatosHelper baseDatosHelper;
    private RecyclerView mRecyclerView;
    private RespuestaSolicitudesAdapter mAdapter;
    private BaseDatosHelper mDbHelper;

    private static RespuestaSolicitudFragment instance;
    public static RespuestaSolicitudFragment newInstance() {
        return new RespuestaSolicitudFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes.RespuestaSolicitudViewModel respuestaSolicitudViewModel =
                new ViewModelProvider(this).get(sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes.RespuestaSolicitudViewModel.class);
        {



            instance = this;
            MainActivity act = (MainActivity) getActivity();

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("Respuestas de solicitud");
            }
            binding = FragmentRespuestaSolicitudBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            baseDatosHelper = new BaseDatosHelper(getContext());
            String usuariooordinador = act.infoUsuario[0];
            Cursor cursor = baseDatosHelper.getUsuario(usuariooordinador);
            String idUsuario = cursor.getString(0);
            String nombreCoordindo = cursor.getString(1);

            final TextView textView = binding.textSolicitudhorario;

            respuestaSolicitudViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
            respuestaSolicitudViewModel.getText().observe(getViewLifecycleOwner(), newText -> {
                // texto para la texview
                textView.setText(getString(R.string.solicitudes_de_horario) +": " + nombreCoordindo);
            });
            //Llenar RecyclerView
            mRecyclerView = root.findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new RespuestaSolicitudesAdapter(getContext(), null, nombreCoordindo);
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

            return root;

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerView();
    }

    //Instancia para abrir en otros class/frgamnts/activities
    public static RespuestaSolicitudFragment getInstance() {
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