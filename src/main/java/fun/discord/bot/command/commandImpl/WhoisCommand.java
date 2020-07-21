package fun.discord.bot.command.commandImpl;

import fun.discord.bot.command.Command;
import fun.discord.bot.command.CommandContext;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Profile(value = "prod")
@Component
public class WhoisCommand implements Command {

    private List<String> replies = new ArrayList<>();

    @PostConstruct
    public void init() {
        replies.add("Конечно же это: ");
        replies.add("Я думаю это: ");
        replies.add("Ни на что не намекаю, но это: ");
        replies.add("Однозначно это: ");
        replies.add("Сам был не уверен, но это: ");
        replies.add("Здесь такой только один и это: ");
        replies.add("Я знаю это: ");
    }

    @Override
    public void handle(CommandContext ctx) {
        final List<Member> members = ctx.getGuild().getMembers();
        final TextChannel channel = ctx.getChannel();

        Random rand = new Random();
        Member randomMember;
        do {
            randomMember = members.get(rand.nextInt(members.size()));
        } while (randomMember.getUser().isBot());
        channel.sendMessage(replies.get(rand.nextInt(replies.size())) + randomMember.getUser().getAsMention()).queue();
    }

    @Override
    public String getName() {
        return "whois";
    }

    @Override
    public String getHelp() {
        return "Используя машинное обучение ~~(нет)~~ определяет пользователя по вашему вопросу\n" +
                "Использование: `b!whois [текст_вопроса]`";
    }
}
