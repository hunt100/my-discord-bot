package fun.discord.bot.command.commandImpl;

import fun.discord.bot.command.Command;
import fun.discord.bot.command.CommandContext;
import fun.discord.bot.command.CommandManager;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Profile(value = "prod")
@Component
public class HelpCommand implements Command {

    @Value("${discord.prefix}")
    private String prefix;

    private final CommandManager manager;

    @Lazy
    @Autowired
    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if (args.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("List of commands\n");
            manager.getCommands().stream().map(Command::getName).forEach(
                    s -> builder.append('`').append(prefix).append(s).append("`\n")
            );

            channel.sendMessage(builder.toString()).queue();
            return;
        }

        String search = args.get(0);
        Command command = manager.getCommand(search);

        if (command == null) {
            channel.sendMessage("Nothing found for " + search).queue();
            return;
        }
        channel.sendMessage(command.getHelp()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Show the list with commands in the bot\n Usage: `b!help [command]`";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("commands", "cmds", "commandlist");
    }
}
