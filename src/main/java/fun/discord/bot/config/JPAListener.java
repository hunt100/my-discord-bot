package fun.discord.bot.config;

import fun.discord.bot.command.CommandManager;
import fun.discord.bot.service.ScamUrlFilterService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;

@Slf4j
@Configuration
public class JPAListener extends ListenerAdapter {

    private static final String URL_PATTERN_MATCHER = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    private final CommandManager commandManager;
    private final GlobalConfig globalConfig;
    private final ScamUrlFilterService scamUrlFilterService;

    @Value("${discord.prefix}")
    private String prefix;

    @Value("${discord.owner.id}")
    private String ownerId;

    @Autowired
    public JPAListener(CommandManager commandManager, GlobalConfig globalConfig, ScamUrlFilterService scamUrlFilterService) {
        this.commandManager = commandManager;
        this.globalConfig = globalConfig;
        this.scamUrlFilterService = scamUrlFilterService;
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        log.info("{} is ready. ", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        User user = event.getAuthor();
        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

        //Need test, regexp doesnt work
        if (globalConfig.isFlag() && event.getMessage().getContentDisplay().contains(URL_PATTERN_MATCHER)) {
            if (scamUrlFilterService.isScamWebsite(event)) {
                event.getChannel().sendMessage("Нашел скам!!!").queue();
            }
        }

        String raw = event.getMessage().getContentRaw();

        if (raw.equalsIgnoreCase(prefix + "shutdown") && user.getId().equals(ownerId)) {
            event.getChannel().sendMessage("Ня пока...").queue();
            log.info("Shutdown bot!");
            event.getJDA().shutdown();
            return;
        }
        if (raw.startsWith(prefix)) {
            commandManager.handle(event);
        }
    }
}
