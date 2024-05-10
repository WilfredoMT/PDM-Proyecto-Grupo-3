package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;

import java.util.Objects;

import sv.edu.ues.fia.pdm.proyecto.grupo3.MainActivity;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        MainActivity act =(MainActivity) getActivity();

        homeViewModel.getText().observe(getViewLifecycleOwner(), newText -> {
            // texto para la TextView
            textView.setText(getString(R.string.bienvenido)+ act.infoUsuario[0]+ " " + getString(R.string.sistema_reserva));
        });


        Log.e("homeFragment", act.infoUsuario[0]);
        Log.e("homeFragment", act.infoUsuario[1]);



        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Acciones FAB
        //FaB visible

        ((MainActivity) requireActivity()).binding.appBarMain.fab.setVisibility(View.VISIBLE);

        ((MainActivity) requireActivity()).setFabIcon(ContextCompat.getDrawable(requireContext(), R.drawable.menu));

        // click listener para este fragment
        ((MainActivity) requireActivity()).setFabClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).abrirMenu();



            }
        });



    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) requireActivity()).binding.appBarMain.fab.setVisibility(View.GONE);
        binding = null;
    }


}