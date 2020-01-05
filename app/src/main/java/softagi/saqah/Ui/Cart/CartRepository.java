package softagi.saqah.Ui.Cart;

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
import softagi.saqah.Models.CartModel;
import softagi.saqah.Models.ProductModel;

class CartRepository
{
    private MutableLiveData<List<CartModel>> cartMutableLiveData = new MutableLiveData<>();

    MutableLiveData<List<CartModel>> getCart(String url, Context context)
    {
        List<CartModel> cartModels = new ArrayList<>();

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
                            JSONObject cart = response.getJSONObject(i);

                            int productId = cart.getInt("product id");
                            int quantity = cart.getInt("quantity");
                            String image = cart.getString("main img");
                            String title = cart.getString("product title");
                            int price = cart.getInt("price after coupon");

                            CartModel cartModel = new CartModel(productId,quantity, price, title, image);
                            cartModels.add(cartModel);
                        }

                        cartMutableLiveData.setValue(cartModels);
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                },
                error ->
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show());

        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

        return cartMutableLiveData;
    }
}