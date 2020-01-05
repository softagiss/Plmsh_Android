package softagi.saqah.Models;

import com.google.gson.annotations.SerializedName;

public class UserModel
{
    @SerializedName("username")
    private String fullname;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("role")
    private String role;

    public UserModel()
    {

    }

    public UserModel(String fullname, String email, String mobile)
    {
        this.fullname = fullname;
        this.email = email;
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
