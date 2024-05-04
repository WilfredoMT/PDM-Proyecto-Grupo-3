package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.eventos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EventosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Menu Eventos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}