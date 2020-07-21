package fun.discord.bot.command.commandImpl;

import fun.discord.bot.command.Command;
import fun.discord.bot.command.CommandContext;
import fun.discord.bot.service.AnimeNekoService;
import fun.discord.bot.service.animeneko.enums.AnimeNekoType;
import fun.discord.bot.util.MessageBuilderUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile(value = "prod")
@Component
public class NekoCommand implements Command {

    private final AnimeNekoService nekoService;

    @Autowired
    public NekoCommand(AnimeNekoService nekoService) {
        this.nekoService = nekoService;
    }

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel textChannel = ctx.getChannel();
        try {
            String randomUrl = nekoService.getAnimeUrl(AnimeNekoType.NEKO);
            Message message =MessageBuilderUtil.createSimpleMessage(null, ctx.getSelfUser(), randomUrl, 0xE100EE73);
            textChannel.sendMessage(message).queue();
        } catch (Exception e) {
            textChannel.sendMessage("Что-то пошло не так. Проверьте логи").queue();
        }

    }

    @Override
    public String getName() {
        return "neko";
    }

    @Override
    public String getHelp() {
        return "Достает случайную аниме-картинку(неко)\n" +
                "Использование: `b!neko`";
    }
}
