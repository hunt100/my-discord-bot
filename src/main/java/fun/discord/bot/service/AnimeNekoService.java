package fun.discord.bot.service;

import com.google.gson.Gson;
import fun.discord.bot.service.animeneko.enums.AnimeNekoType;
import fun.discord.bot.service.animeneko.response.AnimeNekoResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnimeNekoService {

    private static final String REQUEST_URL = "https://neko-love.xyz/api/v1/";

    public String getAnimeUrl(AnimeNekoType type) {
        AnimeNekoResponse response = makeRequest(type);
        if (response.getCode() == 200) {
            return response.getUrl();
        } else {
            log.error("AnimeNekoService Error: {}", response.getMessage());
            throw new RuntimeException(response.getMessage());
        }
    }

    private AnimeNekoResponse makeRequest(AnimeNekoType type) {
        try(CloseableHttpClient httpclient = HttpClients.createDefault();) {
            HttpGet request = new HttpGet(REQUEST_URL + type.getUrlStr());
            log.info("Executing request " + request.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(request);
            Gson gson = new Gson();
            return gson.fromJson(
                    EntityUtils.toString(response.getEntity()), AnimeNekoResponse.class);
        } catch (Exception e) {
            log.error("Error: ", e);
            return new AnimeNekoResponse(500, null, "Something is broken, check manually");
        }
    }
}
