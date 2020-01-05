package softagi.saqah.Models;

import com.google.gson.annotations.SerializedName;

public class WishlistModel
{
    @SerializedName("product id")
    private int productId;
    @SerializedName("price after sale")
    private String price;
    @SerializedName("product title")
    private String title;
    @SerializedName("long description")
    private String description;
    @SerializedName("main img")
    private String image;

    public WishlistModel() {
    }

    public WishlistModel(int productId, String price, String title, String description, String image) {
        this.productId = productId;
        this.price = price;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
