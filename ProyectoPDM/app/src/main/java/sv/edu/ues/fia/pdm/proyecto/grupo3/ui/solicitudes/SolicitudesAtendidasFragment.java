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
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentSolicitudesAtendidasBinding;
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentSolicitudesPropuestasBinding;

public class SolicitudesAtendidasFragment extends Fragment {

    private SolicitudesAtendidasViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private SolicitudesAtendidasAdapter mAdapter;
    private BaseDatosHelper mDbHelper;
    private FragmentSolicitudesAtendidasBinding binding;
    private BaseDatosHelper baseDatosHelper;
    private static SolicitudesAtendidasFragment instance;

    public static SolicitudesAtendidasFragment newInstance() {
        return new SolicitudesAtendidasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSolicitudesAtendidasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.propuestas_atendidas);
        }

        //Llenar RecyclerView
        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SolicitudesAtendidasAdapter(getContext(), null);
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
        mViewModel = new ViewModelProvider(this).get(SolicitudesAtendidasViewModel.class);
        // TODO: Use the ViewModel
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerView();
    }

    //Instancia para abrir en otros class/frgamnts/activities
    public static SolicitudesAtendidasFragment getInstance() {
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