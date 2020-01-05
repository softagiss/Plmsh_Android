package softagi.saqah.Ui.WishList;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import softagi.saqah.Models.CartModel;
import softagi.saqah.Models.ProductModel;
import softagi.saqah.Models.WishlistModel;
import softagi.saqah.R;
import softagi.saqah.Ui.Cart.CartActivity;
import softagi.saqah.Ui.ProductDetails.ProductDetailsActivity;

public class WishlistFragment extends Fragment
{
    private View view;
    private AlertDialog progressDialog;
    private wishlistAdapter adapter;
    private WishlistViewModel wishlistViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.wishlist_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initProgress();
        initRecycler();
        initViewModel();
        initPref();
    }

    @SuppressLint("CommitPrefEdits")
    private void initPref()
    {
        getActivity();
        SharedPreferences loginPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String id = loginPreferences.getString("id", "0");
        String url = "https://plmsh.app/ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-wishlist.php?get=wishlist&id=9";
        getWishlist(url,getContext());
    }

    private void getWishlist(String urd, Context context)
    {
        progressDialog.show();
        wishlistViewModel.getWishlist(urd, context).observe(this, wishlistModels ->
        {
            progressDialog.dismiss();
            //adapter.setWishlistModels(wishlistModels);
        });
    }

    private void initViewModel()
    {
        wishlistViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(WishlistViewModel.class);
    }

    private void initProgress()
    {
        progressDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setMessage(getResources().getString(R.string.register_content))
                .setCancelable(false)
                .build();
    }

    private void initRecycler()
    {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new wishlistAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    class wishlistAdapter extends RecyclerView.Adapter<wishlistAdapter.wishlistVH>
    {
        List<WishlistModel> wishlistModels = new ArrayList<>();

        @NonNull
        @Override
        public wishlistVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.cart_item, parent,false);
            return new wishlistVH(view);
        }

        void setWishlistModels(List<WishlistModel> models)
        {
            this.wishlistModels = models;
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(@NonNull wishlistVH holder, int position)
        {
            WishlistModel wishlistModel = wishlistModels.get(position);

            String title = wishlistModel.getTitle();
            String price = wishlistModel.getPrice();
            String img = wishlistModel.getImage();

            Picasso.get()
                    .load(img)
                    .error(R.drawable.logo1024)
                    .placeholder(R.drawable.logo1024)
                    .into(holder.img);

            holder.title.setText(title);
            holder.price.setText(price + " " + getResources().getString(R.string.total2));
        }

        @Override
        public int getItemCount()
        {
            return wishlistModels.size();
        }

        class wishlistVH extends RecyclerView.ViewHolder
        {
            ImageView delete,img;
            TextView title,price;
            Spinner quantity;

            wishlistVH(@NonNull View itemView)
            {
                super(itemView);

                delete = itemView.findViewById(R.id.delete_cart);
                img = itemView.findViewById(R.id.cart_img);
                title = itemView.findViewById(R.id.cart_title);
                price = itemView.findViewById(R.id.cart_price);
                quantity = itemView.findViewById(R.id.cart_quantity);

                quantity.setVisibility(View.GONE);

                delete.setOnClickListener(v -> new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.delete1)
                        .setMessage(R.string.delete2)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, (dialog, which) ->
                        {
                            // Continue with delete operation
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .show());
            }
        }
    }
}