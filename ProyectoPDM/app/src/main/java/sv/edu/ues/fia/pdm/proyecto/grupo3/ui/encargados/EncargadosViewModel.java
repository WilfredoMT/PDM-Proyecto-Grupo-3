package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.encargados;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EncargadosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EncargadosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Menu Encargados");
    }

    public LiveData<String> getText() {
        return mText;
    }
}