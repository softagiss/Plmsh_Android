package softagi.saqah.Ui.Orders;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import softagi.saqah.Data.RetrofitHelper;
import softagi.saqah.R;
import softagi.saqah.Ui.OrderDetails.OrderDetailsFragment;

public class OrdersActivity extends AppCompatActivity {
    @BindView(R.id.step_view)
    StepView stepView;
    private AlertDialog progressDialog;
    private RetrofitHelper helper;
    private int id;
    private OrderDetailsFragment orderDetailsFragment;

    private RecyclerView recyclerView;
    //private productsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);

        initToolbar();
        initProgress();
        initViews();
        initRecycler();
        initRetrofit();
        initPref();
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://plmsh.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        helper = retrofit.create(RetrofitHelper.class);
    }

    @SuppressLint("CommitPrefEdits")
    private void initPref() {
        SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        id = loginPreferences.getInt("id", 0);
        //getCartWithRetorfit(id);
    }

    /*private void getCartWithRetorfit(int id)
    {
        progressDialog.show();

        Call<List<CartModel>> call = helper.getCart("cart", id);
        call.enqueue(new Callback<List<CartModel>>()
        {
            @Override
            public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response)
            {
                progressDialog.dismiss();

                if (response.isSuccessful() && response.code() == 200)
                {
                    if (response.body() != null)
                    {
                        List<CartModel> productModelList = response.body();
                        //productModels.addAll(productModelList);

                        adapter = new CartActivity.productsAdapter(productModelList);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable t)
            {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void initRecycler()
    {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void initViews()
    {
        recyclerView = findViewById(R.id.recyclerview);
        stepView.go(1, true);
        stepView.getState().steps(new ArrayList<String>()
        {
            {
                add("First");
                add("Second");
                add("Third");
            }
        });
    }

    private void initProgress() {
        progressDialog = new SpotsDialog.Builder()
                .setContext(OrdersActivity.this)
                .setMessage(getResources().getString(R.string.register_content))
                .setCancelable(false)
                .build();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white);
        getSupportActionBar().setTitle(getResources().getString(R.string.orders));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showOrderDetails()
    {
        orderDetailsFragment = (OrderDetailsFragment) getSupportFragmentManager().findFragmentByTag(OrderDetailsFragment.TAG);

        if (orderDetailsFragment == null)
        {

            orderDetailsFragment = OrderDetailsFragment.newInstance();
        }
        orderDetailsFragment.show(getSupportFragmentManager(),OrderDetailsFragment.TAG);
    }
}
