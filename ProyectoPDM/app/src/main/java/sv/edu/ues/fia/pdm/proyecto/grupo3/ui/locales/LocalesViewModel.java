package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocalesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LocalesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Menu Locales");
    }

    public LiveData<String> getText() {
        return mText;
    }
}