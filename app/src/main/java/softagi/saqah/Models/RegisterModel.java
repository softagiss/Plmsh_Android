package softagi.saqah.Models;

import com.google.gson.annotations.SerializedName;

public class RegisterModel
{
    @SerializedName("access_token")
    private String access;

    public RegisterModel(String access)
    {
        this.access = access;
    }

    public String getAccess()
    {
        return access;
    }

    public void setAccess(String access)
    {
        this.access = access;
    }
}
