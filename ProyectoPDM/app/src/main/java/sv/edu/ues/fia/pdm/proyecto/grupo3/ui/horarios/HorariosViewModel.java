package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.horarios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HorariosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HorariosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Menu Horarios");
    }

    public LiveData<String> getText() {
        return mText;
    }
}