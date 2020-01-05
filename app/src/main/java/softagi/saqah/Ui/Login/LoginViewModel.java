package softagi.saqah.Ui.Login;

import android.app.AlertDialog;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel
{
    private LoginRepository loginRepository;

    public LoginViewModel()
    {
        loginRepository = new LoginRepository();
    }

    MutableLiveData<Integer> userLogin(String username, String password)
    {
        return loginRepository.userLogin(username,password);
    }
}
