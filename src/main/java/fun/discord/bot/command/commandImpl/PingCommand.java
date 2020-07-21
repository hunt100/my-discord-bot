package fun.discord.bot.command.commandImpl;

import fun.discord.bot.command.Command;
import fun.discord.bot.command.CommandContext;
import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile(value = "prod")
@Component
public class PingCommand implements Command {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();
        jda.getRestPing().queue(ping -> ctx.getChannel()
                .sendMessageFormat("Reset ping: %sms\nWS ping: %sms", ping, jda.getGatewayPing())
                .queue());
    }

    @Override
    public String getHelp() {
        return "Shows the current ping on discord server";
    }

    @Override
    public String getName() {
        return "ping";
    }
}
