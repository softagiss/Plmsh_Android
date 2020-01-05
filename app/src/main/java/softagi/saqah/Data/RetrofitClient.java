package softagi.saqah.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import softagi.saqah.Models.CartModel;
import softagi.saqah.Models.CategoryModel;
import softagi.saqah.Models.ProductModel;
import softagi.saqah.Models.WishlistModel;

public class RetrofitClient
{
    private static final String BASE_URL = "https://plmsh.app/";
    private RetrofitHelper retrofitHelper;
    private static RetrofitClient retrofitClient;

    private RetrofitClient()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitHelper = retrofit.create(RetrofitHelper.class);
    }

    public static RetrofitClient getInstance()
    {
        if (retrofitClient == null)
        {
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public Call<List<ProductModel>> getProducts()
    {
        return retrofitHelper.getProducts("products", 0);
    }

    public Call<Integer> userLogin(String username, String password)
    {
        return retrofitHelper.login("signin", username, password);
    }

    public Call<Integer> userRegister(String username, String email, String password)
    {
        return retrofitHelper.register("signup", username, password, email);
    }

    public Call<List<CartModel>> getCart(int id)
    {
        return retrofitHelper.getCart("cart", id);
    }

    public Call<List<CategoryModel>> getCategories()
    {
        return retrofitHelper.getCategories("categories", 0);
    }

    public Call<List<WishlistModel>> getWishlist(int id)
    {
        return retrofitHelper.getWishlist("wishlist", id);
    }
}