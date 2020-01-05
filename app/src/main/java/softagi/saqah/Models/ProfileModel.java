package softagi.saqah.Models;

import com.google.gson.annotations.SerializedName;

public class ProfileModel
{
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("avatar")
    private String image;
    @SerializedName("phone")
    private String mobile;
    @SerializedName("id")
    private String id;

    public ProfileModel(String name, String email, String image, String mobile, String id) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.mobile = mobile;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
