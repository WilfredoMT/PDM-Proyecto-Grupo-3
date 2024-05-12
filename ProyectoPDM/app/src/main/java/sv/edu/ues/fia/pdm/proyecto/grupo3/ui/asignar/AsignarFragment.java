package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.asignar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.MainActivity;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentAsignarBinding;
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentCoordinadoresBinding;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.coordinadores.AgregarCoordinadoresActivity;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.coordinadores.CoordinadoresAdapter;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.coordinadores.CoordinadoresFragment;

public class AsignarFragment extends Fragment {


    private AsignarViewModel mViewModel;
    //Instancia
    private static AsignarFragment instance;
    private BaseDatosHelper baseDatosHelper;

    private RecyclerView mRecyclerView;
    private AsignarAdapter mAdapter;
    private FragmentAsignarBinding binding;

    public static AsignarFragment newInstance() {
        return new AsignarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentAsignarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Asignar Materias y Grupos");
        }
        //referencia a mainActivity
        MainActivity act =(MainActivity) getActivity();

        //inicializar instancia de fragment
        instance = this;

        //Datos iniciales
        Log.e("AsignarFragment", "llega");


        baseDatosHelper = new BaseDatosHelper(getContext());
        baseDatosHelper.insertarDatosInicialesGrupoAsignatura();

        //Llenar RecyclerView
        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new AsignarAdapter(getContext(), null);
        mRecyclerView.setAdapter(mAdapter);

        baseDatosHelper = new BaseDatosHelper(getContext());
        Cursor cursor = baseDatosHelper.getReadableDatabase().query(
                BaseDatosHelper.GRUPOASIGNATURA_TABLA,
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

                Intent intent = new Intent(getActivity(), AgregarAsignacionActivity.class);

                startActivity(intent);



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
    public static AsignarFragment getInstance() {
        return instance;
    }

    public void refreshRecyclerView() {

        ((MainActivity) requireActivity()).binding.appBarMain.fab.setVisibility(View.VISIBLE);

        Cursor cursor = baseDatosHelper.getReadableDatabase().query(
                BaseDatosHelper.GRUPOASIGNATURA_TABLA,
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