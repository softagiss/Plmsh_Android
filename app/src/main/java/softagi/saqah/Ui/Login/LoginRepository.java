package softagi.saqah.Ui.Login;

import android.app.AlertDialog;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import softagi.saqah.Data.RetrofitClient;
import softagi.saqah.Models.ProductModel;

class LoginRepository
{
    private MutableLiveData<Integer> loginMutableLiveData = new MutableLiveData<>();

    MutableLiveData<Integer> userLogin(String username, String password)
    {
        RetrofitClient.getInstance().userLogin(username,password).enqueue(new Callback<Integer>()
        {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response)
            {
                loginMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t)
            {

            }
        });
        return loginMutableLiveData;
    }
}
