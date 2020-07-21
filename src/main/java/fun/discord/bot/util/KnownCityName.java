package fun.discord.bot.util;

import java.util.HashMap;
import java.util.Map;

public class KnownCityName {

    private Map<String, String> cityKeys;


    public KnownCityName() {
        this.cityKeys = new HashMap<>();
        this.cityKeys.put("Almaty", "Алматы");
        this.cityKeys.put("Atyrau", "Атырау");
        this.cityKeys.put("London", "Лондон");
    }

    public String getLocalizedNameIfExist(String cityName) {
        return cityKeys.get(cityName) != null ? cityKeys.get(cityName) : cityName;
    }
}
