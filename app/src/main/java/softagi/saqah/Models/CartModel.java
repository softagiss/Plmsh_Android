package softagi.saqah.Models;

import com.google.gson.annotations.SerializedName;

public class CartModel
{
    @SerializedName("product id")
    private int productId;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("price after coupon")
    private int priceAfterSale;
    @SerializedName("product title")
    private String title;
    @SerializedName("main img")
    private String img;

    public CartModel() {
    }

    public CartModel(int productId, int quantity, int priceAfterSale, String title, String img)
    {
        this.productId = productId;
        this.quantity = quantity;
        this.priceAfterSale = priceAfterSale;
        this.title = title;
        this.img = img;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPriceAfterSale() {
        return priceAfterSale;
    }

    public void setPriceAfterSale(int priceAfterSale) {
        this.priceAfterSale = priceAfterSale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
