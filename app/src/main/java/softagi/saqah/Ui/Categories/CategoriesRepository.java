package softagi.saqah.Ui.Categories;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import softagi.saqah.Data.RetrofitClient;
import softagi.saqah.Models.CartModel;
import softagi.saqah.Models.CategoryModel;

class CategoriesRepository
{
    private MutableLiveData<List<CategoryModel>> getCategoriesMutableLiveData = new MutableLiveData<>();

    MutableLiveData<List<CategoryModel>> getCategories()
    {
        RetrofitClient.getInstance().getCategories().enqueue(new Callback<List<CategoryModel>>()
        {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response)
            {
                getCategoriesMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t)
            {

            }
        });
        return getCategoriesMutableLiveData;
    }
}
