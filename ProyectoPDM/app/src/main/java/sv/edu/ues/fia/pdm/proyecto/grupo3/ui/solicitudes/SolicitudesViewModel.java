package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SolicitudesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SolicitudesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Menu Solicitudes");
    }

    public LiveData<String> getText() {
        return mText;
    }
}