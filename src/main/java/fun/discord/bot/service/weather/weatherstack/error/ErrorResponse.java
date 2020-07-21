
package fun.discord.bot.service.weather.weatherstack.error;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("error")
    @Expose
    private Error error;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "success=" + success +
                ", error=" + error +
                '}';
    }
}
