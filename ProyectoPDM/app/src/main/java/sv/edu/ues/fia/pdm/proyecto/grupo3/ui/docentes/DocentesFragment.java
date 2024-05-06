package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.docentes;

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

import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentDocentesBinding;

public class DocentesFragment extends Fragment {

    private FragmentDocentesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sv.edu.ues.fia.pdm.proyecto.grupo3.ui.docentes.DocentesViewModel docentesViewModel =
                new ViewModelProvider(this).get(sv.edu.ues.fia.pdm.proyecto.grupo3.ui.docentes.DocentesViewModel.class);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Docentes");
        }

        binding = FragmentDocentesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textSlideshow;
        docentesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}