package softagi.saqah.Ui.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import softagi.saqah.Data.MySingleton;
import softagi.saqah.Data.RetrofitHelper;
import softagi.saqah.Models.CartModel;
import softagi.saqah.Models.DefaultModel;
import softagi.saqah.R;
import softagi.saqah.Ui.CheckOut.CheckOutActivity;
import softagi.saqah.Ui.Login.LoginActivity;
import softagi.saqah.Ui.Main.MainActivity;
import softagi.saqah.Ui.OrderDetails.OrderDetailsFragment;
import softagi.saqah.Ui.Register.RegisterActivity;

public class CartActivity extends AppCompatActivity
{
    private AlertDialog progressDialog;
    private TextView q,t,shop_now;
    private LinearLayout linearLayout;
    private CartViewModel cartViewModel;
    int qu = 0,to = 0;

    private RecyclerView recyclerView;
    private cartAdapter adapter;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initToolbar();
        initProgress();
        initViews();
        initRecycler();
        initViewModel();
        initPref();
    }

    private void initViewModel()
    {
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
    }

    @SuppressLint("CommitPrefEdits")
    private void initPref()
    {
        SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        id = loginPreferences.getString("id", "0");
        String url = "https://plmsh.app/ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-cart.php?get=cart&id=" + id;
        getCart(url, getApplicationContext());
    }

    private void getCart(String url, Context context)
    {
        cartViewModel.getCart(url,context).observe(this, cartModels ->
        {
            progressDialog.dismiss();

            if (cartModels.size() == 0)
            {
                linearLayout.setVisibility(View.VISIBLE);
                q.setText("0");
                t.setText("0");
                shop_now.setOnClickListener(v ->
                {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(CartActivity.this).toBundle());
                });
            } else
            {
                linearLayout.setVisibility(View.GONE);
                adapter.setCartModels(cartModels);
            }
        });
    }

    private void initRecycler()
    {
        adapter = new cartAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void initViews()
    {
        recyclerView = findViewById(R.id.recyclerview);
    }

    public void checkout(View view)
    {
        Intent intent = new Intent(getApplicationContext(), CheckOutActivity.class);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(CartActivity.this).toBundle());
    }

    class cartAdapter extends RecyclerView.Adapter<cartAdapter.cartVH>
    {
        List<CartModel> cartModels = new ArrayList<>();
        int i = 0;

        @NonNull
        @Override
        public cartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cart_item, parent,false);
            return new cartVH(view);
        }

        void setCartModels(List<CartModel> models)
        {
            this.cartModels = models;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(@NonNull cartVH holder, int position)
        {
            CartModel cartModel = cartModels.get(position);

            String title = cartModel.getTitle();
            int priceAfterSale = cartModel.getPriceAfterSale();
            int quantity = cartModel.getQuantity();
            int productId = cartModel.getProductId();
            String img = cartModel.getImg();

            Picasso.get()
                    .load(img)
                    .error(R.drawable.logo1024)
                    .placeholder(R.drawable.logo1024)
                    .into(holder.img);

            holder.title.setText(title);
            holder.price.setText(priceAfterSale + " " + getResources().getString(R.string.total2));

            holder.quantity.setSelection(quantity);

            holder.delete.setOnClickListener(v ->
            {
                progressDialog.show();

                String url = "https://plmsh.app/ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-cart.php?remove=cart&productid=" + productId;

                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET,
                        url,
                        response ->
                        {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                            /*try
                            {
                                JSONObject message = response.getJSONObject(0);
                                String msg = message.getString("message");
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            }catch (JSONException e)
                            {
                                e.printStackTrace();
                            }*/
                        },
                        error ->
                        {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        });

                MySingleton.getInstance(CartActivity.this).addToRequestQueue(stringRequest);

                /*Call<Integer> call = helper.removeFromCart("cart", productId, " text/html; charset=UTF-8");
                call.enqueue(new Callback<Integer>()
                {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response)
                    {
                        progressDialog.dismiss();

                        if (response.isSuccessful())
                        {
                            if (response.body() !=null && response.body() == 1)
                            {
                                Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/
            });

            qu = qu + quantity;
            to = to + priceAfterSale;

            q.setText(String.valueOf(qu));
            t.setText(String.valueOf(to));
        }

        @Override
        public int getItemCount()
        {
            return cartModels.size();
        }

        class cartVH extends RecyclerView.ViewHolder
        {
            ImageView delete,img;
            TextView title,price;
            Spinner quantity;

            cartVH(@NonNull View itemView)
            {
                super(itemView);

                delete = itemView.findViewById(R.id.delete_cart);
                img = itemView.findViewById(R.id.cart_img);
                title = itemView.findViewById(R.id.cart_title);
                price = itemView.findViewById(R.id.cart_price);
                quantity = itemView.findViewById(R.id.cart_quantity);

                setQuantity(quantity);
            }

            void setQuantity(Spinner spinner)
            {
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                        R.array.quantity, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
        }
    }

    private void initProgress()
    {
        progressDialog = new SpotsDialog.Builder()
                .setContext(CartActivity.this)
                .setMessage(getResources().getString(R.string.register_content))
                .setCancelable(false)
                .build();
        progressDialog.show();
    }

    private void initToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        q = findViewById(R.id.cart_quantity);
        t = findViewById(R.id.cart_total);
        linearLayout = findViewById(R.id.not_found_lin);
        shop_now = findViewById(R.id.shop_now);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white);
        getSupportActionBar().setTitle(getResources().getString(R.string.cart));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home :
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}