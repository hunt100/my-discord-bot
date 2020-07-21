package fun.discord.bot.command.commandImpl;

import fun.discord.bot.command.Command;
import fun.discord.bot.command.CommandContext;
import fun.discord.bot.config.GlobalConfig;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "prod")
@Slf4j
public class FlagCommand implements Command {

    private final GlobalConfig globalConfig;

    @Autowired
    public FlagCommand(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    @Override
    public void handle(CommandContext ctx) {
        globalConfig.setFlag(!globalConfig.isFlag());
        log.info("Change flag");
        final TextChannel channel = ctx.getChannel();
        channel.sendMessage("Начинаю мониторить чат...").queue();
    }

    @Override
    public String getName() {
        return "flag";
    }

    @Override
    public String getHelp() {
        return "flag";
    }
}
