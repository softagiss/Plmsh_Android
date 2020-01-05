package softagi.saqah.Ui.WishList;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

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
import softagi.saqah.Models.WishlistModel;

class WishlistRepository
{
    private MutableLiveData<List<WishlistModel>> getWishlistMutableLiveData = new MutableLiveData<>();

    MutableLiveData<List<WishlistModel>> getWishlist(String url, Context context)
    {
        List<WishlistModel> wishlistModels = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                response ->
                {
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    /*try
                    {
                        for(int i = 0 ; i < response.length() ; i++)
                        {
                            JSONObject wishlist = response.getJSONObject(i);

                            int productId = wishlist.getInt("product id");
                            String price = wishlist.getString("price after sale");
                            String title = wishlist.getString("product title");
                            String description = wishlist.getString("long description");
                            String image = wishlist.getString("main img");

                            WishlistModel wishlistModel  = new WishlistModel(productId,price, title, description, image);
                            wishlistModels.add(wishlistModel);
                        }

                        getWishlistMutableLiveData.setValue(wishlistModels);
                        Toast.makeText(context, wishlistModels.size() + "", Toast.LENGTH_SHORT).show();
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }*/
                },
                error ->
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show());

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

        return getWishlistMutableLiveData;
    }
}