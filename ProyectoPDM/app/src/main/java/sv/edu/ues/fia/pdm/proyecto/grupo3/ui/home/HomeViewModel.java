package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("Bienvenido/a ");
    }

    public LiveData<String> getText() {
        return mText;
    }
}