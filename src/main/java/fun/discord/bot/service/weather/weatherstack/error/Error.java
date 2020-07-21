
package fun.discord.bot.service.weather.weatherstack.error;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("info")
    @Expose
    private String info;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Error{" +
                "code=" + code +
                ", type='" + type + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
