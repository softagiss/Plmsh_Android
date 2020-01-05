package softagi.saqah.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PlaceModel implements Serializable
{
    @SerializedName("place id")
    private String id;
    @SerializedName("place title")
    private String title;

    public PlaceModel() {
    }

    public PlaceModel(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
