package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RespuestaSolicitudViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public RespuestaSolicitudViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Menu Solicitudes");
    }

    public LiveData<String> getText() {
        return mText;
    }
}