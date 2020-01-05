package softagi.saqah.Ui.Main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import softagi.saqah.Ui.Categories.CategoriesFragment;
import softagi.saqah.Ui.Home.HomeFragment;
import softagi.saqah.Ui.More.MoreFragment;
import softagi.saqah.R;
import softagi.saqah.Ui.WishList.WishlistFragment;

public class MainActivity extends AppCompatActivity
{
    BottomNavigationView navigation;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);

        fragmentManager = getSupportFragmentManager();

        HomeFragment homeFragment = new HomeFragment();
        loadFragment(homeFragment, "homeFragment");

        navigation.setOnNavigationItemSelectedListener(item ->
        {
            switch (item.getItemId())
            {
                case R.id.home:
                    HomeFragment homeFragment1 = new HomeFragment();
                    loadFragment(homeFragment1, "homeFragment");
                    return true;
                case R.id.wishlist:
                    WishlistFragment wishlistFragment = new WishlistFragment();
                    loadFragment(wishlistFragment, "wishlistFragment");
                    return true;
                case R.id.categories:
                    CategoriesFragment categoriesFragment = new CategoriesFragment();
                    loadFragment(categoriesFragment,"categoriesFragment");
                    return true;
                case R.id.more:
                    MoreFragment moreFragment = new MoreFragment();
                    loadFragment(moreFragment, "moreFragment");
                    return true;
            }
            return false;
        });
    }

    public void loadFragment(Fragment fragment, String tag)
    {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment, tag);
        //fragmentTransaction.addToBackStack(null);

        //fragmentManager.popBackStack();
        // Commit the transaction
        fragmentTransaction.commit();
    }

    private long exitTime = 0;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void doExitApp()
    {
        if ((System.currentTimeMillis() - exitTime) > 2000)
        {
            Toast.makeText(this, getResources().getString(R.string.exit), Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finishAffinity();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed()
    {
        doExitApp();
    }
}