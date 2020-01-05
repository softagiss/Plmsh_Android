package softagi.saqah.Ui.Categories;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import softagi.saqah.Models.CategoryModel;
import softagi.saqah.Models.ProductModel;
import softagi.saqah.R;
import softagi.saqah.Ui.Cart.CartActivity;
import softagi.saqah.Ui.Cart.CartViewModel;
import softagi.saqah.Ui.Main.MainActivity;
import softagi.saqah.Ui.Products.ProductsFragment;

public class CategoriesFragment extends Fragment
{
    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<List<ProductModel>> products;
    private CategoriesViewModel categoriesViewModel;
    private AlertDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.categories_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initViewModel();
        initViews();
        initProgress();
        getCategories();
    }

    private void initViewModel()
    {
        categoriesViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(CategoriesViewModel.class);
    }

    private void initViews()
    {
        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewpager);

        products = new ArrayList<>();
    }

    private void getCategories()
    {
        progressDialog.show();
        categoriesViewModel.getCategories().observe(this, categoryModels ->
        {
            if (categoryModels.size() > 0)
            {
                FragmentPagerAdapter adapter;

                for (int i = 0 ; i < categoryModels.size() ; i ++)
                {
                    tabLayout.addTab(tabLayout.newTab().setText(categoryModels.get(i).getName()));
                    products.add(categoryModels.get(i).getProductModels());
                }

                adapter = new FragmentPagerAdapter(getChildFragmentManager(),tabLayout.getTabCount(),products);
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(1);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                //tabLayout.setupWithViewPager(viewPager);
                tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

                /*if (tabLayout.getTabCount() == 2)
                {
                    tabLayout.setTabMode(TabLayout.MODE_FIXED);
                } else {
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                }*/
            }

            progressDialog.dismiss();
        });
    }

    private void initProgress()
    {
        progressDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setMessage(getResources().getString(R.string.register_content))
                .setCancelable(false)
                .build();
    }

    public class FragmentPagerAdapter extends FragmentStatePagerAdapter
    {
        int tabs;
        List<List<ProductModel>> productModels;

        FragmentPagerAdapter(FragmentManager fm, int tabs, List<List<ProductModel>> productModels)
        {
            super(fm);
            this.tabs = tabs;
            this.productModels = productModels;
        }

        @Override
        public Fragment getItem(int position)
        {
            List<ProductModel> productModelList = new ArrayList<>();

            for (int i = 0 ; i < tabs ; i ++)
            {
                productModelList = productModels.get(position);
            }

            return ProductsFragment.newInstance(productModelList);
        }

        @Override
        public int getCount()
        {
            return tabs;
        }
    }
}