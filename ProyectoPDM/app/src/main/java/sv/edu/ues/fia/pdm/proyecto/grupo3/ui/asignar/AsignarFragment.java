package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.asignar;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sv.edu.ues.fia.pdm.proyecto.grupo3.R;

public class AsignarFragment extends Fragment {

    private AsignarViewModel mViewModel;

    public static AsignarFragment newInstance() {
        return new AsignarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asignar, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AsignarViewModel.class);
        // TODO: Use the ViewModel
    }

}