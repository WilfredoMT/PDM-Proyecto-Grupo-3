package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentSolicitudeshorarioBinding;

public class SolicitudesHorarioFragment extends Fragment {

    private FragmentSolicitudeshorarioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SolicitudesHorarioViewModel solicitudesHorarioViewModel =
                new ViewModelProvider(this).get(SolicitudesHorarioViewModel.class);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Solicitudes");
        }

        binding = FragmentSolicitudeshorarioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textSlideshow;
        solicitudesHorarioViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}