package softagi.saqah.Models;

import com.google.gson.annotations.SerializedName;

public class DefaultModel
{
    @SerializedName("message")
    private String message;

    public DefaultModel(String message) {
        this.message = message;
    }

    public DefaultModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
