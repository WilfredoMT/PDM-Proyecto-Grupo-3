package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo;

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

import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentCicloBinding;
import android.os.Handler;
import android.os.Looper;

public class CicloFragment extends Fragment {

    private FragmentCicloBinding binding;
    private BaseDatosHelper baseDatosHelper;
    private RecyclerView mRecyclerView;
    private CicloAdapter mAdapter;
    private BaseDatosHelper mDbHelper;

    //Instancia
    private static CicloFragment instance;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.CicloViewModel cicloViewModel =  new ViewModelProvider(this).get(sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.CicloViewModel.class);

        instance = this;


        //referencia a mainActivity
        MainActivity act =(MainActivity) getActivity();
        //Barra
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Ciclo");
        }

        binding = FragmentCicloBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Datos iniciales
        baseDatosHelper = new BaseDatosHelper(getContext());
        baseDatosHelper.insertarDatosInicialesCiclo();

        final TextView textView = binding.textCiclo;
        cicloViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        cicloViewModel.getText().observe(getViewLifecycleOwner(), newText -> {
            // texto para la texview
            textView.setText(getString(R.string.ciclostodos));
        });

        //Llenar RecyclerView
        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CicloAdapter(getContext(), null);
        mRecyclerView.setAdapter(mAdapter);

        mDbHelper = new BaseDatosHelper(getContext());
        Cursor cursor = mDbHelper.getReadableDatabase().query(
                BaseDatosHelper.CICLO_TABLA,
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

                Intent intent = new Intent(getActivity(), AgregarCicloActivity.class);

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
    public static CicloFragment getInstance() {
        return instance;
    }

    public void refreshRecyclerView() {
        ((MainActivity) requireActivity()).binding.appBarMain.fab.setVisibility(View.VISIBLE);
        Cursor cursor = mDbHelper.getReadableDatabase().query(
                BaseDatosHelper.CICLO_TABLA,
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