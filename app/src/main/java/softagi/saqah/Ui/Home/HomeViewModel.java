package softagi.saqah.Ui.Home;

import android.app.AlertDialog;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import softagi.saqah.Data.RetrofitClient;
import softagi.saqah.Models.ProductModel;

public class HomeViewModel extends ViewModel
{
    private HomeRepository homeRepository;

    public HomeViewModel()
    {
        homeRepository = new HomeRepository();
    }

    MutableLiveData<List<ProductModel>> getProducts(String url, Context context)
    {
        return homeRepository.getProducts(url, context);
    }
}
