package softagi.saqah.Ui.Products;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import softagi.saqah.Models.ProductModel;
import softagi.saqah.R;
import softagi.saqah.Ui.ProductDetails.ProductDetailsActivity;

public class ProductsFragment extends Fragment
{
    private View view;
    private productsAdapter adapter;

    public static ProductsFragment newInstance(List<ProductModel> productModels)
    {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putSerializable("products", (Serializable) productModels);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.products_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initRecycler();
        initSearch();
    }

    private void initRecycler()
    {
        LinearLayout linearLayout = view.findViewById(R.id.not_found_lin);
        RecyclerView recyclerview = view.findViewById(R.id.recyclerview);
        adapter = new productsAdapter();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(adapter);

        if (getArguments() != null)
        {
            List<ProductModel> productModels = (List<ProductModel>) getArguments().getSerializable("products");

            if (productModels != null)
            {
                if (productModels.size() == 0)
                {
                    linearLayout.setVisibility(View.VISIBLE);
                } else
                {
                    adapter.setProductModels(productModels);
                }
            }
        }
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
        public void onBindViewHolder(@NonNull productsAdapter.productsVH holder, int position) {
            ProductModel productModel = productModels.get(position);

            String img = productModel.getImg();
            String storeTitle = productModel.getPlaceModels().get(0).getTitle();
            String productTitle = productModel.getTitle();
            String price = productModel.getPrice();
            String regularPrice = productModel.getRegularPrice();

            Picasso.get()
                    .load(img)
                    .error(R.drawable.logo1024)
                    .placeholder(R.drawable.logo1024)
                    .into(holder.img);

            holder.title.setText(productTitle);
            holder.store.setText(storeTitle);
            holder.fPrice.setText(regularPrice + " " + getResources().getString(R.string.total2));
            holder.sPrice.setText(price + " " + getResources().getString(R.string.total2));

            holder.itemView.setOnClickListener(v -> {
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
        public int getItemCount()
    {
            return productModels.size();
        }

        void setProductModels(List<ProductModel> models)
    {
            this.productModels = models;
            this.filterdProductModels = new ArrayList<>(models);
            notifyDataSetChanged();
        }

        private Filter exampleFilter = new Filter()
        {
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
        public Filter getFilter()
    {
            return exampleFilter;
        }

        class productsVH extends RecyclerView.ViewHolder
        {
            ImageView img;
            TextView store, title, fPrice, sPrice;

            productsVH(@NonNull View itemView) {
                super(itemView);

                img = itemView.findViewById(R.id.prod_img);
                store = itemView.findViewById(R.id.store_title);
                title = itemView.findViewById(R.id.prod_title);
                fPrice = itemView.findViewById(R.id.first_price);
                sPrice = itemView.findViewById(R.id.second_price);
            }
        }
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
}
