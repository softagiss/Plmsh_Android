package softagi.saqah.Ui.OrderDetails;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import softagi.saqah.R;

public class OrderDetailsFragment extends DialogFragment
{
    public static final String TAG = OrderDetailsFragment.class.getSimpleName();
    private View view;

    public static OrderDetailsFragment newInstance()
    {
        return new OrderDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.order_details_fragment,container,false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return view;
    }
}
