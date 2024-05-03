package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CicloViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CicloViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Menu Ciclo");
    }

    public LiveData<String> getText() {
        return mText;
    }
}