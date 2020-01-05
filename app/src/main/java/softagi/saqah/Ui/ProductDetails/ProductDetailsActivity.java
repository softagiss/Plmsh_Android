package softagi.saqah.Ui.ProductDetails;

import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import softagi.saqah.Models.ProductModel;
import softagi.saqah.R;

public class ProductDetailsActivity extends AppCompatActivity
{
    @BindView(R.id.imageSlider)
    SliderView imageSlider;
    @BindView(R.id.store_title)
    TextView storeTitle;
    @BindView(R.id.prod_title)
    TextView prodTitle;
    @BindView(R.id.first_price)
    TextView firstPrice;
    @BindView(R.id.second_price)
    TextView secondPrice;
    @BindView(R.id.description_field)
    TextView descriptionField;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private ProductModel productModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);

        initAnimation();
        initToolbar();
        initViews();
        initSlider();
    }

    private void initSlider() {
        imageSlider = findViewById(R.id.imageSlider);
        imageSlider.setSliderAdapter(new sliderAdapter(productModel.getGallery()));
    }

    private void initViews()
    {
        productModel = (ProductModel) getIntent().getSerializableExtra("product_model");

        if (productModel != null)
        {
            String stitle = productModel.getPlaceModels().get(0).getTitle();
            String productTitle = productModel.getTitle();
            String price = productModel.getPrice();
            String regularPrice = productModel.getRegularPrice();
            String desription = productModel.getLongDescription();

            prodTitle.setText(productTitle);
            storeTitle.setText(stitle);
            firstPrice.setText(regularPrice + " " + getResources().getString(R.string.total2));
            secondPrice.setText(price + " " + getResources().getString(R.string.total2));
            descriptionField.setText(desription);
        }

        reviewsAdapter adapter = new reviewsAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        recycler.addItemDecoration(dividerItemDecoration);
        recycler.setAdapter(adapter);
    }

    private void initAnimation() {
        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white);
        getSupportActionBar().setTitle(getResources().getString(R.string.productdetails));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class sliderAdapter extends SliderViewAdapter<sliderAdapter.sliderVH>
    {
        List<String> img;

        sliderAdapter(List<String> img) {
            this.img = img;
        }

        @Override
        public sliderVH onCreateViewHolder(ViewGroup parent)
        {
            View inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.slide_item, parent, false);
            return new sliderVH(inflate);
        }

        @Override
        public void onBindViewHolder(sliderVH viewHolder, int position)
        {
            String url = img.get(position);
            Picasso.get()
                    .load(url)
                    .error(R.drawable.logo1024)
                    .placeholder(R.drawable.logo1024)
                    .into(viewHolder.imageViewBackground);
        }

        @Override
        public int getCount()
        {
            //slider view count could be dynamic size
            return img.size();
        }

        class sliderVH extends SliderViewAdapter.ViewHolder
        {
            ImageView imageViewBackground;

            sliderVH(View itemView)
            {
                super(itemView);
                imageViewBackground = itemView.findViewById(R.id.image_slide);
            }
        }
    }

    public class reviewsAdapter extends RecyclerView.Adapter<reviewsAdapter.reviewViewHolder> {

        @NonNull
        @Override
        public reviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.review_item, parent, false);
            return new reviewViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull reviewViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 4;
        }

        class reviewViewHolder extends RecyclerView.ViewHolder {

            reviewViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}