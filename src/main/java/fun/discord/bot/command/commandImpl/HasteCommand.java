package fun.discord.bot.command.commandImpl;

import fun.discord.bot.command.Command;
import fun.discord.bot.command.CommandContext;
import fun.discord.bot.service.HastebinService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Profile(value = "prod")
@Component
public class HasteCommand implements Command {

    private final HastebinService hastebinService;

    @Autowired
    public HasteCommand(HastebinService hastebinService) {
        this.hastebinService = hastebinService;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if (args.size() < 2) {
            channel.sendMessage("Missing arguments").queue();
            return;
        }

        /*
        final boolean raw = Boolean.parseBoolean(args.get(0));
        final String body = String.join(" ", args.subList(1, args.size()));
        */
        final boolean raw = Boolean.parseBoolean(args.get(0));
        final String contentRaw = ctx.getMessage().getContentRaw();
        final int index = contentRaw.indexOf(args.get(0)) + args.get(0).length();
        final String body = contentRaw.substring(index).trim();

        try {
            String url = hastebinService.post(body, raw);
            channel.sendMessageFormat("%s", url).queue();
        } catch (Exception e) {
            log.error("Error appeared: ", e);
            return;
        }
    }

    @Override
    public String getName() {
        return "haste";
    }

    @Override
    public String getHelp() {
        return "create a paste on hastebin\n" +
                "Post things at your own risk.\n" +
                "Usage: `b!haste [raw] [text]`";
    }
}
