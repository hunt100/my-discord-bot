package fun.discord.bot.service;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ScamUrlFilterService {

    private static final String URL_PATTERN_MATCHER = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public boolean isScamWebsite(GuildMessageReceivedEvent event) {
        Pattern pattern = Pattern.compile(URL_PATTERN_MATCHER);
        Matcher matcher = pattern.matcher(event.getMessage().getContentRaw());
        if (matcher.find()) {
            String str = matcher.group(1);
            return str.equals("https://vk.com/feed");
        }
        return false;
    }
}
