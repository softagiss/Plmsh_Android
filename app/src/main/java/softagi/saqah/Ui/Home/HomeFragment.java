package softagi.saqah.Ui.Home;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import softagi.saqah.Data.MySingleton;
import softagi.saqah.Data.RetrofitHelper;
import softagi.saqah.Models.ProductModel;
import softagi.saqah.R;
import softagi.saqah.Ui.Main.MainActivity;
import softagi.saqah.Ui.ProductDetails.ProductDetailsActivity;

public class HomeFragment extends Fragment
{
    private View view;
    private AlertDialog progressDialog;
    private productsAdapter adapter;
    private HomeViewModel homeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initProgress();
        initRecycler();
        initSearch();
        initViewModel();
        getHome();
        initRefresh();
        initPref();
    }

    @SuppressLint("CommitPrefEdits")
    private void initPref()
    {
        getActivity();
        SharedPreferences loginPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String id = loginPreferences.getString("id", "0");
    }

    private void getHome()
    {
        progressDialog.show();
        String url = "https://plmsh.app/ktlgccxshounbufhxzko-softagi-api/qrvfxekmbbprvrckkdyc-products.php?get=products&id=0";
        homeViewModel.getProducts(url, getContext()).observe(this, productModels ->
        {
            progressDialog.dismiss();
            adapter.setProductModels(productModels);
        });
    }

    private void initRefresh()
    {
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
                () ->
                {
                    Fragment frg = null;
                    frg = Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentByTag("homeFragment");
                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.detach(Objects.requireNonNull(frg));
                    ft.attach(frg);
                    ft.commit();
                }
        );
    }

    private void initViewModel()
    {
        homeViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(HomeViewModel.class);
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
        RecyclerView recyclerview = view.findViewById(R.id.recyclerview);
        adapter = new productsAdapter();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(adapter);
    }

    private void initSearch()
    {
        SearchView searchview = view.findViewById(R.id.searchview);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    class productsAdapter extends RecyclerView.Adapter<productsAdapter.productsVH> implements Filterable
    {
        List<ProductModel> productModels = new ArrayList<>();
        List<ProductModel> filterdProductModels = new ArrayList<>();

        @NonNull
        @Override
        public productsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);
            return new productsVH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull productsVH holder, int position)
        {
            ProductModel productModel = productModels.get(position);

            //int productId = productModel.getId();
            String img = productModel.getImg();
            //String storeTitle = productModel.getPlaceModels().get(0).getTitle();
            String productTitle = productModel.getTitle();
            String price = productModel.getPrice();
            //String regularPrice = productModel.getRegularPrice();

            Picasso.get()
                    .load(img)
                    .error(R.drawable.logo1024)
                    .placeholder(R.drawable.logo1024)
                    .into(holder.img);

            holder.title.setText(productTitle);
            //holder.store.setText(storeTitle);
            //holder.fPrice.setText(regularPrice + " " + getResources().getString(R.string.total2));
            holder.sPrice.setText(price + " " + getResources().getString(R.string.total2));

            holder.itemView.setOnClickListener(v ->
            {
                Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
                Pair pair = Pair.create(holder.img, ViewCompat.getTransitionName(holder.img));
                Pair pair1 = Pair.create(holder.store, ViewCompat.getTransitionName(holder.store));
                Pair pair2 = Pair.create(holder.title, ViewCompat.getTransitionName(holder.title));
                Pair pair3 = Pair.create(holder.fPrice, ViewCompat.getTransitionName(holder.fPrice));
                Pair pair4 = Pair.create(holder.sPrice, ViewCompat.getTransitionName(holder.sPrice));
                intent.putExtra("product_model", productModel);
                ActivityOptions options5 = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pair, pair1, pair2, pair3, pair4);
                startActivity(intent, options5.toBundle());
            });
        }

        @Override
        public int getItemCount() {
            return productModels.size();
        }

        void setProductModels(List<ProductModel> models)
        {
            this.productModels = models;
            this.filterdProductModels = new ArrayList<>(models);
            notifyDataSetChanged();
        }

        private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ProductModel> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(filterdProductModels);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (ProductModel item : filterdProductModels) {
                        String s = item.getTitle();
                        if (s.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productModels.clear();
                productModels.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };

        @Override
        public Filter getFilter() {
            return exampleFilter;
        }

        class productsVH extends RecyclerView.ViewHolder {
            ImageView img;
            TextView store, title, fPrice, sPrice, addToCart;

            productsVH(@NonNull View itemView)
            {
                super(itemView);

                img = itemView.findViewById(R.id.prod_img);
                store = itemView.findViewById(R.id.store_title);
                title = itemView.findViewById(R.id.prod_title);
                fPrice = itemView.findViewById(R.id.first_price);
                sPrice = itemView.findViewById(R.id.second_price);
                addToCart = itemView.findViewById(R.id.add_to_cart_text);
            }
        }
    }

    private void customToast(String body)
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, Objects.requireNonNull(getActivity()).findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.text);
        text.setText(body);

        Toast toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}