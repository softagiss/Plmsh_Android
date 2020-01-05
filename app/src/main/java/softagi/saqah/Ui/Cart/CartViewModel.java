package softagi.saqah.Ui.Cart;

import android.app.AlertDialog;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import softagi.saqah.Models.CartModel;

public class CartViewModel extends ViewModel
{
    private CartRepository cartRepository;

    public CartViewModel()
    {
        cartRepository = new CartRepository();
    }

    MutableLiveData<List<CartModel>> getCart(String url, Context context)
    {
        return cartRepository.getCart(url,context);
    }
}