package softagi.saqah.Ui.More;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import softagi.saqah.Ui.Cart.CartActivity;
import softagi.saqah.Ui.EditInfo.EditActivity;
import softagi.saqah.Ui.Login.LoginActivity;
import softagi.saqah.Ui.Main.MainActivity;
import softagi.saqah.Ui.Orders.OrdersActivity;
import softagi.saqah.R;

public class MoreFragment extends Fragment
{
    private View view;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.more_fragment, container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initPref();
        initViews();
    }

    @SuppressLint("CommitPrefEdits")
    private void initPref()
    {
        getContext();
        loginPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
    }

    private void initViews()
    {
        TextView loginTxt = view.findViewById(R.id.login_txt);
        LinearLayout cart = view.findViewById(R.id.cart);
        LinearLayout orders = view.findViewById(R.id.orders);
        LinearLayout editInfo = view.findViewById(R.id.edit_info);
        LinearLayout contact = view.findViewById(R.id.contact);
        LinearLayout about = view.findViewById(R.id.about);

        String id = loginPreferences.getString("id", "0");

        if (!id.equals("0"))
        {
            editInfo.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getContext(), EditActivity.class);
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }
            });

            cart.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getContext(), CartActivity.class);
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }
            });

            orders.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getContext(), OrdersActivity.class);
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }
            });

            loginTxt.setText(R.string.logout);

            loginTxt.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    loginPrefsEditor.putInt("id", 0);
                    loginPrefsEditor.apply();

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }
            });
        } else
            {
                editInfo.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent,
                                ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    }
                });

                orders.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent,
                                ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    }
                });

                cart.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent,
                                ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    }
                });

                loginTxt.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent,
                                ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    }
                });
            }

        /*about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), AboutActivity.class);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });

        contact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01065632190"));
                startActivity(intent);
            }
        });*/
    }
}
