package softagi.saqah.Ui.CheckOut;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import softagi.saqah.Data.RetrofitHelper;
import softagi.saqah.Models.DefaultModel;
import softagi.saqah.R;
import softagi.saqah.Ui.Cart.CartActivity;

public class CheckOutActivity extends AppCompatActivity
{
    @BindView(R.id.first_name_field)
    EditText firstNameField;
    @BindView(R.id.second_name_field)
    EditText secondNameField;
    @BindView(R.id.company_field)
    EditText companyField;
    @BindView(R.id.email_field)
    EditText emailField;
    @BindView(R.id.mobile_field)
    EditText mobileField;
    @BindView(R.id.first_address_field)
    EditText firstAddressField;
    @BindView(R.id.second_address_field)
    EditText secondAddressField;
    @BindView(R.id.city_field)
    EditText cityField;
    @BindView(R.id.cart_quantity)
    TextView cartQuantity;
    @BindView(R.id.cart_total)
    TextView cartTotal;

    private RetrofitHelper helper;
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        ButterKnife.bind(this);

        initToolbar();
        initProgress();
        initRetrofit();
    }
    private void initProgress()
    {
        progressDialog = new SpotsDialog.Builder()
                .setContext(CheckOutActivity.this)
                .setMessage(getResources().getString(R.string.register_content))
                .setCancelable(false)
                .build();
    }


    private void initRetrofit()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://plmsh.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        helper = retrofit.create(RetrofitHelper.class);
    }

    private void initToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white);
        getSupportActionBar().setTitle(getResources().getString(R.string.checkoutlabel));
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

    public void checkout(View view)
    {
        String firstName = firstNameField.getText().toString();
        String secondName = secondNameField.getText().toString();
        String company = companyField.getText().toString();
        String email = emailField.getText().toString();
        String mobile = mobileField.getText().toString();
        String firstAddress = firstAddressField.getText().toString();
        String secondAddress = secondAddressField.getText().toString();
        String city = cityField.getText().toString();

        if (firstName.isEmpty())
        {
            customToast(getResources().getString(R.string.fname));
            return;
        }

        if (secondName.isEmpty())
        {
            customToast(getResources().getString(R.string.lname));
            return;
        }

        if (company.isEmpty())
        {
            customToast(getResources().getString(R.string.company));
            return;
        }

        if (email.isEmpty())
        {
            customToast(getResources().getString(R.string.emailfield));
            return;
        }

        if (mobile.isEmpty())
        {
            customToast(getResources().getString(R.string.mobile));
            return;
        }

        if (firstAddress.isEmpty())
        {
            customToast(getResources().getString(R.string.address1));
            return;
        }

        if (secondAddress.isEmpty())
        {
            customToast(getResources().getString(R.string.address2));
            return;
        }

        if (city.isEmpty())
        {
            customToast(getResources().getString(R.string.city));
            return;
        }

        progressDialog.show();
        addOrderWithRetrofit(firstName, secondName, company, email, mobile, firstAddress, secondAddress, city);
    }

    private void addOrderWithRetrofit(String firstName, String secondName, String company, String email, String mobile, String firstAddress, String secondAddress, String city)
    {
        Call<List<DefaultModel>> call = helper.addOrder("order", firstName, secondName, company, email, mobile, firstAddress, secondAddress, "Egypt", city, "egypt","11211");
        call.enqueue(new Callback<List<DefaultModel>>()
        {
            @Override
            public void onResponse(Call<List<DefaultModel>> call, Response<List<DefaultModel>> response)
            {
                progressDialog.dismiss();

                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        String res = response.body().get(0).getMessage();
                        customToast(res);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DefaultModel>> call, Throwable t)
            {
                progressDialog.dismiss();
                customToast(t.getMessage());
            }
        });
    }

    private void customToast(String body)
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.text);
        text.setText(body);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}