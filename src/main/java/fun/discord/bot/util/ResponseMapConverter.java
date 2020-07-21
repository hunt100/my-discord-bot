package fun.discord.bot.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ResponseMapConverter {

    public static Map<String, Object> convertObjectToMap(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(object, Map.class);
    }
}
