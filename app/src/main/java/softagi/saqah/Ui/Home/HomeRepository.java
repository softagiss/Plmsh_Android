package softagi.saqah.Ui.Home;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import softagi.saqah.Data.MySingleton;
import softagi.saqah.Data.RetrofitClient;
import softagi.saqah.Models.ProductModel;

class HomeRepository
{
    private MutableLiveData<List<ProductModel>> productMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> addProductToCartMutableLiveData = new MutableLiveData<>();

    MutableLiveData<List<ProductModel>> getProducts(String url, Context context)
    {
        List<ProductModel> productModels = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response ->
                {
                    try
                    {
                        for(int i = 0 ; i < response.length() ; i++)
                        {
                            JSONObject product = response.getJSONObject(i);

                            String image = product.getString("main img");
                            String title = product.getString("product title");
                            String price = product.getString("price");

                            ProductModel productModel = new ProductModel(title,image, price);
                            productModels.add(productModel);
                        }

                        productMutableLiveData.setValue(productModels);
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                },
                error ->
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show());

        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

        return productMutableLiveData;
    }
}