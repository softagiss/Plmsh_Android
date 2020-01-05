package softagi.saqah.Ui.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.Objects;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import softagi.saqah.Data.MySingleton;
import softagi.saqah.Data.RetrofitHelper;
import softagi.saqah.R;
import softagi.saqah.Ui.Register.RegisterActivity;
import softagi.saqah.Ui.Main.MainActivity;

public class LoginActivity extends AppCompatActivity
{
    private SharedPreferences.Editor loginPrefsEditor;
    private EditText emailField,passwordField;
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initPref();
        initProgress();
        initToolbar();
        initViews();
    }

    private void initProgress()
    {
        progressDialog = new SpotsDialog.Builder()
                .setContext(LoginActivity.this)
                .setMessage(getResources().getString(R.string.register_content))
                .setCancelable(false)
                .build();
    }

    private void initViews()
    {
        String email = getIntent().getStringExtra("email");
        String pass = getIntent().getStringExtra("password");

        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_field);

        if (email != null && pass != null)
        {
            emailField.setText(email);
            passwordField.setText(pass);
        }
    }

    @SuppressLint("CommitPrefEdits")
    private void initPref()
    {
        SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
    }

    private void initToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white);
        getSupportActionBar().setTitle(getResources().getString(R.string.login));
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

    public void login(View view)
    {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (email.isEmpty())
        {
            customToast((getResources().getString(R.string.email)));
            return;
        }

        if (password.length() < 6)
        {
            customToast((getResources().getString(R.string.password)));
            return;
        }

        progressDialog.show();
        userLogin(email, password);
    }

    private void userLogin(String email, String password)
    {
        String url = "https://plmsh.app/ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-user.php?get=signin&username=" + email + "&password=" + password;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                response ->
                {
                    progressDialog.dismiss();

                    loginPrefsEditor.putString("id", response);
                    loginPrefsEditor.apply();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());
                },
                error ->
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void register(View view)
    {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());
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