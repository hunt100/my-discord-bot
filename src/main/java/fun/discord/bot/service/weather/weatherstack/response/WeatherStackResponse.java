
package fun.discord.bot.service.weather.weatherstack.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherStackResponse {

    @SerializedName("request")
    @Expose
    private Request request;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("current")
    @Expose
    private Current current;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

}
