package softagi.saqah.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductModel implements Serializable
{
    @SerializedName("product id")
    private int id;
    @SerializedName("product title")
    private String title;
    @SerializedName("short description")
    private String shortDescription;
    @SerializedName("long description")
    private String longDescription;
    @SerializedName("main img")
    private String img;
    @SerializedName("gallery img")
    private List<String> gallery;
    @SerializedName("price")
    private String price;
    @SerializedName("regular price")
    private String regularPrice;
    @SerializedName("rate & review")
    private List<ReviewModel> reviewModels;
    @SerializedName("place")
    private List<PlaceModel> placeModels;

    public ProductModel() {
    }

    public ProductModel(String title, String img, String price) {
        this.title = title;
        this.img = img;
        this.price = price;
    }

    public ProductModel(int id, String title, String shortDescription, String longDescription, String img, List<String> gallery, String price, String regularPrice, List<ReviewModel> reviewModels, List<PlaceModel> placeModels) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.img = img;
        this.gallery = gallery;
        this.price = price;
        this.regularPrice = regularPrice;
        this.reviewModels = reviewModels;
        this.placeModels = placeModels;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public List<ReviewModel> getReviewModels() {
        return reviewModels;
    }

    public void setReviewModels(List<ReviewModel> reviewModels) {
        this.reviewModels = reviewModels;
    }

    public List<PlaceModel> getPlaceModels() {
        return placeModels;
    }

    public void setPlaceModels(List<PlaceModel> placeModels) {
        this.placeModels = placeModels;
    }
}
