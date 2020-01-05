package softagi.saqah.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoryModel implements Serializable
{
    @SerializedName("cat id")
    private int id;
    @SerializedName("cat name")
    private String name;
    @SerializedName("products")
    private List<ProductModel> productModels;

    public CategoryModel()
    {
    }

    public CategoryModel(int id, String name, List<ProductModel> productModels)
    {
        this.id = id;
        this.name = name;
        this.productModels = productModels;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductModel> getProductModels() {
        return productModels;
    }

    public void setProductModels(List<ProductModel> productModels) {
        this.productModels = productModels;
    }
}
