package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales;

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

import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.FragmentLocalesBinding;

public class LocalesFragment extends Fragment {

    private FragmentLocalesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales.LocalesViewModel localesViewModel =
                new ViewModelProvider(this).get(sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales.LocalesViewModel.class);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Locales");
        }


        binding = FragmentLocalesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textSlideshow;
        localesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}