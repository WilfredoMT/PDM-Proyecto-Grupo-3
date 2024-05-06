package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.docentes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DocentesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DocentesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Menu Docentes");
    }

    public LiveData<String> getText() {
        return mText;
    }
}