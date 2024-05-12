package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales;

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
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentLocalesBinding;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.AgregarCicloActivity;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.CicloAdapter;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.CicloFragment;

public class LocalesFragment extends Fragment {

    private FragmentLocalesBinding binding;
    //Instancia
    private static LocalesFragment instance;
    private BaseDatosHelper baseDatosHelper;

    private RecyclerView mRecyclerView;
    private LocalesAdapter mAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales.LocalesViewModel localesViewModel =
                new ViewModelProvider(this).get(sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales.LocalesViewModel.class);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        //referencia a mainActivity
        MainActivity act =(MainActivity) getActivity();

        //inicializar instancia de fragment
        instance = this;

        if (actionBar != null) {
            actionBar.setTitle("Locales");
        }

        binding = FragmentLocalesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Datos iniciales
        baseDatosHelper = new BaseDatosHelper(getContext());
        baseDatosHelper.insertarDatosInicialesLocal();
        baseDatosHelper.insertarDatosInicialesEscuela();


        final TextView textView = binding.textLocales;
        localesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        // texto para la texview
        localesViewModel.getText().observe(getViewLifecycleOwner(), newText -> {
        textView.setText(R.string.todos_los_locales_registrados);
        });

        //Llenar RecyclerView
        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new LocalesAdapter(getContext(), null);
        mRecyclerView.setAdapter(mAdapter);

        baseDatosHelper = new BaseDatosHelper(getContext());
        Cursor cursor = baseDatosHelper.getReadableDatabase().query(
                BaseDatosHelper.LOCAL_TABLA,
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

                Intent intent = new Intent(getActivity(), AgregarLocalesActivity.class);

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
    public static LocalesFragment getInstance() {
        return instance;
    }

    public void refreshRecyclerView() {

        ((MainActivity) requireActivity()).binding.appBarMain.fab.setVisibility(View.VISIBLE);

        Cursor cursor = baseDatosHelper.getReadableDatabase().query(
                BaseDatosHelper.LOCAL_TABLA,
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