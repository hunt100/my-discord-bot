package fun.discord.bot.command;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
public class CommandManager {

    private final List<Command> commands;

    @Value("${discord.prefix}")
    private String prefix;

    @Autowired
    public CommandManager(List<Command> commands) {
        this.commands = commands;
    }

    public List<Command> getCommands() {
        return commands;
    }

    @Nullable
    public Command getCommand(String search) {
        String searchLower = search.toLowerCase();
        for (Command command : this.commands) {
            if (command.getName().equals(searchLower) || command.getAliases().contains(searchLower)) {
                return command;
            }
        }
        return null;
    }

    public void handle(GuildMessageReceivedEvent event) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(prefix), "")
                .split("\\s+");
        String invoke = split[0].toLowerCase();
        Command command = this.getCommand(invoke);

        if (command != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            command.handle(ctx);
        } else {
            log.warn("Command with name {} was not found, return null", invoke);
        }
    }
}
