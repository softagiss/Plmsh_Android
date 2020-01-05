package softagi.saqah.Ui.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import softagi.saqah.Data.RetrofitClient;
import softagi.saqah.Data.RetrofitHelper;
import softagi.saqah.R;
import softagi.saqah.Ui.Login.LoginActivity;

public class RegisterActivity extends AppCompatActivity
{
    private AlertDialog progressDialog;
    private EditText usernameField,emailField,passwordField,confirmField;
    private RetrofitHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initToolbar();
        initProgress();
        initViews();
        initRetrofit();
    }

    private void initRetrofit()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://plmsh.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        helper = retrofit.create(RetrofitHelper.class);
    }

    private void initViews()
    {
        usernameField = findViewById(R.id.username_field);
        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_field);
        confirmField = findViewById(R.id.confirm_password_field);
    }

    private void initToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white);
        getSupportActionBar().setTitle(getResources().getString(R.string.registernow));
    }

    private void initProgress()
    {
        progressDialog = new SpotsDialog.Builder()
                .setContext(RegisterActivity.this)
                .setMessage(getResources().getString(R.string.register_content))
                .setCancelable(false)
                .build();
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

    public void register(View view)
    {
        String username = usernameField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String confirm = confirmField.getText().toString();

        if (username.isEmpty())
        {
            customToast(getResources().getString(R.string.usernamefield));
            return;
        }

        if (email.isEmpty())
        {
            customToast(getResources().getString(R.string.emailfield));
            return;
        }

        if (password.length() < 6)
        {
            customToast(getResources().getString(R.string.password));
            return;
        }

        if (!confirm.equalsIgnoreCase(password))
        {
            customToast(getResources().getString(R.string.confirmpassword));
            return;
        }

        progressDialog.show();
        registerWithRetrofit(username,email,password);
    }

    private void registerWithRetrofit(String username, final String email,String password)
    {
        Call<Integer> call = helper.register("signup",username,password,email);
        call.enqueue(new Callback<Integer>()
        {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response)
            {
                if (response.isSuccessful() && response.code() == 200)
                {
                    if (response.body() != null)
                    {
                        progressDialog.dismiss();

                        if (response.body() == 1)
                        {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("password", password);
                            startActivity(intent,
                                    ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this).toBundle());
                            return;
                        }

                        if (response.body() == 3)
                        {
                            customToast(getResources().getString(R.string.usernameexist));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t)
            {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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