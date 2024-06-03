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

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.MainActivity;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentRespuestaSolicitudBinding;
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentSolicitudesPropuestasBinding;

public class SolicitudesPropuestasFragment extends Fragment {

    private SolicitudesPropuestasViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private SolicitudesPropuestasAdapter mAdapter;
    private BaseDatosHelper mDbHelper;
    private FragmentSolicitudesPropuestasBinding binding;
    private BaseDatosHelper baseDatosHelper;
    private static SolicitudesPropuestasFragment instance;

    public static SolicitudesPropuestasFragment newInstance() {
        return new SolicitudesPropuestasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        instance = this;
        MainActivity act = (MainActivity) getActivity();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Propuestas registradas");
        }
        binding = FragmentSolicitudesPropuestasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        baseDatosHelper = new BaseDatosHelper(getContext());
        

        //Llenar RecyclerView
        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SolicitudesPropuestasAdapter(getContext(), null);
        mRecyclerView.setAdapter(mAdapter);

        mDbHelper = new BaseDatosHelper(getContext());
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




        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SolicitudesPropuestasViewModel.class);
        // TODO: Use the ViewModel
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerView();
    }

    //Instancia para abrir en otros class/frgamnts/activities
    public static SolicitudesPropuestasFragment getInstance() {
        return instance;
    }

    public void refreshRecyclerView() {
        //((MainActivity) requireActivity()).binding.appBarMain.fab.setVisibility(View.VISIBLE);
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