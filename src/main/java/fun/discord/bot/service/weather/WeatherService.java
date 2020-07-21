package fun.discord.bot.service.weather;

import fun.discord.bot.service.weather.weatherstack.response.WeatherStackResponse;

public interface WeatherService {
    WeatherStackResponse getResponse(String city);
}
