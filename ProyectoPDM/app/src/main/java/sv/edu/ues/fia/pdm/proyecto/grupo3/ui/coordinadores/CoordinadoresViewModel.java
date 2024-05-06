package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.coordinadores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CoordinadoresViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CoordinadoresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Menu Coordinadores");
    }

    public LiveData<String> getText() {
        return mText;
    }
}