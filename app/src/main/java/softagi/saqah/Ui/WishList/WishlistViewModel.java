package softagi.saqah.Ui.WishList;

import android.app.AlertDialog;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import softagi.saqah.Models.ProductModel;
import softagi.saqah.Models.WishlistModel;

public class WishlistViewModel extends ViewModel
{
    private WishlistRepository wishlistRepository;

    public WishlistViewModel()
    {
        wishlistRepository = new WishlistRepository();
    }

    MutableLiveData<List<WishlistModel>> getWishlist(String url, Context context)
    {
        return wishlistRepository.getWishlist(url, context);
    }
}
