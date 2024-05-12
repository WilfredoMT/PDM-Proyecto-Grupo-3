package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.encargados;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentEncargadosBinding;
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentEncargadosBinding;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.encargados.AgregarEncargadosActivity;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.encargados.EncargadosAdapter;


public class EncargadosFragment extends Fragment {

    //Instancia
    private static sv.edu.ues.fia.pdm.proyecto.grupo3.ui.encargados.EncargadosFragment instance;
    private BaseDatosHelper baseDatosHelper;

    private RecyclerView mRecyclerView;
    private sv.edu.ues.fia.pdm.proyecto.grupo3.ui.encargados.EncargadosAdapter mAdapter;
    private FragmentEncargadosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sv.edu.ues.fia.pdm.proyecto.grupo3.ui.encargados.EncargadosViewModel encargadosViewModel =
                new ViewModelProvider(this).get(sv.edu.ues.fia.pdm.proyecto.grupo3.ui.encargados.EncargadosViewModel.class);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Encargado de Horario");
        }

        binding = FragmentEncargadosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //referencia a mainActivity
        MainActivity act =(MainActivity) getActivity();

        //inicializar instancia de fragment
        instance = this;

        final TextView textView = binding.textEncargados;
        encargadosViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //Datos iniciales
        baseDatosHelper = new BaseDatosHelper(getContext());
        baseDatosHelper.insertarDatosInicialesEncargado();

        // texto para la texview
        encargadosViewModel.getText().observe(getViewLifecycleOwner(), newText -> {
            textView.setText(R.string.todos_los_encargados_de_horario_registrados);
        });

        //Llenar RecyclerView
        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new EncargadosAdapter(getContext(), null);
        mRecyclerView.setAdapter(mAdapter);

        baseDatosHelper = new BaseDatosHelper(getContext());
        Cursor cursor = baseDatosHelper.getReadableDatabase().query(
                BaseDatosHelper.ENCARGADO_TABLA,
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

                Intent intent = new Intent(getActivity(), AgregarEncargadosActivity.class);

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
    public static sv.edu.ues.fia.pdm.proyecto.grupo3.ui.encargados.EncargadosFragment getInstance() {
        return instance;
    }

    public void refreshRecyclerView() {

        ((MainActivity) requireActivity()).binding.appBarMain.fab.setVisibility(View.VISIBLE);

        Cursor cursor = baseDatosHelper.getReadableDatabase().query(
                BaseDatosHelper.ENCARGADO_TABLA,
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