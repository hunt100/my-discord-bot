package fun.discord.bot.command.commandImpl;

import fun.discord.bot.command.Command;
import fun.discord.bot.command.CommandContext;
import fun.discord.bot.service.cat.CatService;
import fun.discord.bot.util.MessageBuilderUtil;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile(value = "prod")
@Component
public class CatCommand implements Command {

    private final CatService catService;

    @Autowired
    public CatCommand(CatService catService) {
        this.catService = catService;
    }

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        try {
            String url = catService.getCatImage();
            Message message = MessageBuilderUtil.createSimpleMessage("Картинка с котиком :cat:", ctx.getSelfUser(), url, 0xD3EED700);
            channel.sendMessage(message).queue();
        } catch (Exception e) {
            log.error("Error appeared: ", e);
            return;
        }
    }

    @Override
    public String getName() {
        return "cat";
    }

    @Override
    public String getHelp() {
        return "Случайная картинка со случайным котиком\n" +
                "Использование: `b!cat`";
    }
}