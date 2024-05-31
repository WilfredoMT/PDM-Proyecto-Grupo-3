package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.materias;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.MainActivity;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentMateriasBinding;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.AgregarCicloActivity;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.CicloAdapter;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.CicloFragment;

public class MateriasFragment extends Fragment {

    private FragmentMateriasBinding binding;
    private static MateriasFragment instance;
    private BaseDatosHelper mDbHelper;
    private BaseDatosHelper baseDatosHelper;
    private RecyclerView mRecyclerView;
    private MateriaAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sv.edu.ues.fia.pdm.proyecto.grupo3.ui.materias.MateriasViewModel materiasViewModel =
                new ViewModelProvider(this).get(sv.edu.ues.fia.pdm.proyecto.grupo3.ui.materias.MateriasViewModel.class);
        //barra
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Materias");
        }

        //Instancia de estefragment
        instance = this;
        //referencia a mainActivity
        MainActivity act =(MainActivity) getActivity();

        binding = FragmentMateriasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textMaterias;
        materiasViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        //Datos iniciales
        baseDatosHelper = new BaseDatosHelper(getContext());
        //baseDatosHelper.insertarDatosInicialesAsignatura();

        materiasViewModel.getText().observe(getViewLifecycleOwner(), newText -> {
            // texto para la texview
            textView.setText(getString(R.string.todas_las_materias_registradas));
        });

        //Llenar recyclerView
        mRecyclerView = root.findViewById(R.id.recyclerViewMaterias);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MateriaAdapter(getContext(), null);
        mRecyclerView.setAdapter(mAdapter);

        mDbHelper = new BaseDatosHelper(getContext());
        mDbHelper.getAsignaturas(new BaseDatosHelper.Callback() {
            @Override
            public boolean onSuccess(Cursor cursor) {
                mAdapter.swapCursor(cursor);


                return false;
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();


            }
        });

        //Acciones FAB
        //FaB visible
        ((MainActivity) requireActivity()).binding.appBarMain.fab.setVisibility(View.VISIBLE);

        ((MainActivity) requireActivity()).setFabIcon(ContextCompat.getDrawable(requireContext(), R.drawable.plus));

        // click listener para este fragment
        ((MainActivity) requireActivity()).setFabClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity(), AgregarMateriaActivity.class);

                startActivity(intent);

            }
        });

        return root;
    }

    //Instancia para abrir en otros class/frgamnts/activities
    public static MateriasFragment getInstance() {
        return instance;
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
    public void refreshRecyclerView() {
        ((MainActivity) requireActivity()).binding.appBarMain.fab.setVisibility(View.VISIBLE);
        mDbHelper.getAsignaturas(new BaseDatosHelper.Callback() {
            @Override
            public boolean onSuccess(Cursor cursor) {
                mAdapter.swapCursor(cursor);
                return false;
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}