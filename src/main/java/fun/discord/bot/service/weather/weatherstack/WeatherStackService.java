package fun.discord.bot.service.weather.weatherstack;

import com.google.gson.Gson;
import fun.discord.bot.service.weather.weatherstack.error.ErrorResponse;
import fun.discord.bot.service.weather.weatherstack.response.WeatherStackResponse;
import fun.discord.bot.service.weather.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
public class WeatherStackService implements WeatherService {

    private static final String REQUEST_URL = "http://api.weatherstack.com/current?access_key=%s&query=%s";

    @Value("${weatherstack.api.token}")
    private String token;

    @Override
    public WeatherStackResponse getResponse(String city) {
        String requestURL = String.format(REQUEST_URL, token, city);
        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Weatherstack Java Api");
            conn.setUseCaches(false);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Gson gson = new Gson();
            String unformattedString = reader.readLine();
            if (unformattedString.contains("error")) {
                ErrorResponse error = gson.fromJson(unformattedString, ErrorResponse.class);
                log.error("Bad request or response: Request city: {}. Response: {}", city, error.toString());
                return null;
            }

            return gson.fromJson(unformattedString, WeatherStackResponse.class);
        } catch (Exception e) {
            log.error("Error: ", e);
            return null;
        }
    }
}
