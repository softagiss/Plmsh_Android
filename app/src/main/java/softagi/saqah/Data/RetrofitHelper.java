package softagi.saqah.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import softagi.saqah.Models.CartModel;
import softagi.saqah.Models.CategoryModel;
import softagi.saqah.Models.DefaultModel;
import softagi.saqah.Models.ProductModel;
import softagi.saqah.Models.UserModel;
import softagi.saqah.Models.WishlistModel;

public interface RetrofitHelper
{
    @GET("ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-user.php")
    Call<Integer> login(@Query("get") String get, @Query("username") String username, @Query("password") String password);

    @GET("ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-user.php")
    Call<Integer> register(@Query("get") String get, @Query("username") String username, @Query("password") String password, @Query("email") String email);

    @GET("ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-user.php")
    Call<List<UserModel>> getUser(@Query("get") String get, @Query("id") int id);

    @GET("ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-user.php")
    Call<Integer> updateUser(@Query("edit") String edit, @Query("id") int id, @Query("phone") String mobile);

    @GET("ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-cart.php")
    Call<List<CartModel>> getCart(@Query("get") String get, @Query("id") int id);

    @GET("ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-cart.php")
    Call<Integer> removeFromCart(@Query("remove") String remove, @Query("productid") int productid, @Header("Content-Type") String ContentType);

    @GET("ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-products.php")
    Call<List<ProductModel>> getProducts(@Query("get") String get, @Query("id") int id);

    @GET("ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-cart.php")
    Call<Integer> addProductToCart(@Query("add") String add, @Query("productid") int productid, @Query("quantity") int quantity);

    @GET("ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-category.php")
    Call<List<CategoryModel>> getCategories(@Query("get") String get, @Query("id") int id);

    @GET("ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-wishlist.php")
    Call<List<WishlistModel>> getWishlist(@Query("get") String get, @Query("id") int id);

    @GET("ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-order.php")
    Call<List<DefaultModel>> addOrder
            (@Query("add") String add,
            @Query("first_name") String first_name,
            @Query("last_name") String last_name,
             @Query("company") String company,
             @Query("email") String email,
             @Query("phone") String phone,
             @Query("first_address") String first_address,
             @Query("second_address") String second_address,
             @Query("country") String country,
             @Query("city") String city,
             @Query("state") String state,
             @Query("postal_code") String postal_code);
}