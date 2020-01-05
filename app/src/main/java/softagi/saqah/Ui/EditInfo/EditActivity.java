package softagi.saqah.Ui.EditInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import softagi.saqah.Data.RetrofitHelper;
import softagi.saqah.Models.UserModel;
import softagi.saqah.R;
import softagi.saqah.Ui.Main.MainActivity;

public class EditActivity extends AppCompatActivity
{
    private EditText usernameField,emailField,mobileField;

    private AlertDialog progressDialog;
    private RetrofitHelper helper;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initProgress();
        initRetrofit();
        initViews();
        initToolbar();
        initPref();
    }

    private void initRetrofit()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://plmsh.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        helper = retrofit.create(RetrofitHelper.class);
    }

    private void initProgress()
    {
        progressDialog = new SpotsDialog.Builder()
                .setContext(EditActivity.this)
                .setMessage(getResources().getString(R.string.register_content))
                .setCancelable(false)
                .build();
    }

    private void initViews()
    {
        usernameField = findViewById(R.id.username_field);
        emailField = findViewById(R.id.email_field);
        mobileField = findViewById(R.id.mobile_field);
    }

    @SuppressLint("CommitPrefEdits")
    private void initPref()
    {
        SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        id = loginPreferences.getInt("id", 0);
        getUserWithRetorfit(id);
    }

    private void initToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white);
        getSupportActionBar().setTitle(getResources().getString(R.string.setting));
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

    public void save(View view)
    {
        String mobile = mobileField.getText().toString();

        if (mobile.isEmpty())
        {
            Toast.makeText(getApplicationContext(), R.string.mobile, Toast.LENGTH_SHORT).show();
            mobileField.requestFocus();
            return;
        }

        progressDialog.show();
        updateUserWithRetorfit(mobile,id);
    }

    class reviewWithRetrofit extends AsyncTask<Void, Void, okhttp3.Response>
    {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"product_id\": 120,\r\n  \"review\": \"oiolnoln!\",\r\n  \"reviewer\": \"osama\",\r\n  \"reviewer_email\": \"osama@gmail.com\",\r\n  \"rating\": 3\r\n}");
        Request request = new Request.Builder()
                .url("https://plmsh.app/wp-json/wc/v3/products/reviews?oauth_consumer_key=ck_9342b3038a753686d4c0084af72bcc911e7fd19f&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1575132967&oauth_nonce=XI6evMXr2Bc&oauth_version=1.0&oauth_signature=uuIT93YAn0BnVhnhTgJD7UabaeU=")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "6ecb217a-f063-4785-8611-ddd2c6f5f603")
                .build();

        @Override
        protected okhttp3.Response doInBackground(Void... voids)
        {
            okhttp3.Response response = null;
            try
            {
                response = client.newCall(request).execute();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(okhttp3.Response response)
        {
            if (response.code() == 201)
            {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "review added", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getUserWithRetorfit(int id)
    {
        progressDialog.show();

        Call<List<UserModel>> call = helper.getUser("user", id);
        call.enqueue(new Callback<List<UserModel>>()
        {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response)
            {
                progressDialog.dismiss();

                if (response.isSuccessful() && response.code() == 200)
                {
                    if (response.body() != null)
                    {
                        List<UserModel> userModels = response.body();
                        UserModel userModel = userModels.get(0);

                        usernameField.setText(userModel.getFullname());
                        emailField.setText(userModel.getEmail());
                        mobileField.setText(userModel.getMobile());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t)
            {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserWithRetorfit(String mobile, int id)
    {
        Call<Integer> call = helper.updateUser("phone",id,mobile);
        call.enqueue(new Callback<Integer>()
        {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response)
            {
                if (response.isSuccessful() && response.code() == 200)
                {
                    progressDialog.dismiss();

                    if (response.body() != null)
                    {
                        progressDialog.dismiss();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent,
                                ActivityOptions.makeSceneTransitionAnimation(EditActivity.this).toBundle());

                        Toast.makeText(getApplicationContext(), R.string.savechange, Toast.LENGTH_SHORT).show();
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
}