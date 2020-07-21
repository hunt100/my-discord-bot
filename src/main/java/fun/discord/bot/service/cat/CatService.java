package fun.discord.bot.service.cat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fun.discord.bot.service.cat.response.CatResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CatService {

    private final String GET_ONE_CAT_IMAGE = "https://api.thecatapi.com/v1/images/search?limit=1";
    private final String CAT_URL = "https://api.thecatapi.com/v1";

    public String getCatImage() {
        List<CatResponse> cat = sendRequest();
        if (cat.isEmpty()) {
            return "";
        }
        return cat.get(0).getUrl();
    }

    private List<CatResponse> sendRequest() {
        try(CloseableHttpClient httpclient = HttpClients.createDefault();) {
            HttpGet request = new HttpGet(GET_ONE_CAT_IMAGE);
            log.info("Executing request " + request.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CatResponse>>() {}.getType();
                return gson.fromJson(EntityUtils.toString(response.getEntity()), listType);
            }
            throw new RuntimeException();
        } catch (Exception e) {
            log.error("Error: ", e);
            return new ArrayList<>();
        }
    }
}
