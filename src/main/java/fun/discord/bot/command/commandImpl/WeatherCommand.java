package fun.discord.bot.command.commandImpl;

import fun.discord.bot.command.Command;
import fun.discord.bot.command.CommandContext;
import fun.discord.bot.service.weather.weatherstack.WeatherStackService;
import fun.discord.bot.service.weather.weatherstack.response.WeatherStackResponse;
import fun.discord.bot.util.KnownCityName;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Profile(value = "prod")
@Component
public class WeatherCommand implements Command {

    private final WeatherStackService weatherStackService;

    @Autowired
    public WeatherCommand(WeatherStackService weatherStackService) {
        this.weatherStackService = weatherStackService;
    }

    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();
        if (args.size() < 1) {
            channel.sendMessage(":x: Не хватает аргумента. Воспользуйтесь b!help weather").queue();
            return;
        }
        final String city = args.get(0);

        try {
            WeatherStackResponse response = weatherStackService.getResponse(city);
            if (response == null) {
                channel.sendMessage(":x: Ошибка. Убедитесь, что вы верно ввели название города (или сервис недоступен.)").queue();
                return;
            }
            channel.sendMessage(buildMessage(response, ctx)).queue();
        } catch (Exception e) {
            log.error("Error appeared: ", e);
            return;
        }

    }

    @Override
    public String getName() {
        return "weather";
    }

    @Override
    public String getHelp() {
        return "Проверяет погоду в указанном городе\n" +
                "Использование: `b!weather [название_города]`";
    }

    private Message buildMessage(WeatherStackResponse response, CommandContext ctx) {
        KnownCityName knownCityName = new KnownCityName();
        final String localizedCityName = knownCityName.getLocalizedNameIfExist(response.getLocation().getName());
        final String avatarUrl = ctx.getAuthor().getAvatarUrl();
        final String authorName = ctx.getAuthor().getName();
        final LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return new MessageBuilder()
                .setEmbed(new EmbedBuilder()
                        .setTitle("Температура по запросу в городе - " + localizedCityName)
                        .setDescription("lat:" + response.getLocation().getLat() + " ; lon:" + response.getLocation().getLon())
                        .setColor(new Color(237205))
                        .setTimestamp(OffsetDateTime.parse(dateTimeFormatter.format(now)))
                        .setFooter("Timezone: "+ response.getLocation().getTimezoneId() +
                                " (+" + Double.valueOf(response.getLocation().getUtcOffset()) + ")")
                        .setThumbnail(response.getCurrent().getWeatherIcons().get(0))
                        .setAuthor(authorName, null, avatarUrl)
                        .addField(":regional_indicator_t:", "Температура: " + response.getCurrent().getTemperature() + "°C.", false)
                        .addField(":regional_indicator_w:", "Скорость ветра: " + response.getCurrent().getWindSpeed() + " км/ч.", false)
                        .addField(":regional_indicator_h:", "Влажность: " + response.getCurrent().getHumidity() + "%", false)
                        .build()).build();
    }
}
