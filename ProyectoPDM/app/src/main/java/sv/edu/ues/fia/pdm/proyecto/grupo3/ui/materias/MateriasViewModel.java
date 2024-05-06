package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.materias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MateriasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MateriasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Menu Materias");
    }

    public LiveData<String> getText() {
        return mText;
    }
}