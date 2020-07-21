package fun.discord.bot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class MessageBuilderUtil {

    public static Message createSimpleMessage(String title, User author, String imageUrl, int color) {
        final String avatarUrl = author.getAvatarUrl();
        final String authorName = author.getName();
        return new MessageBuilder()
                .setEmbed(new EmbedBuilder()
                        .setTitle(title)
                        .setColor(new Color(color, true))
                        .setAuthor(authorName, null, avatarUrl)
                        .setImage(imageUrl)
                        .build()).build();
    }
}
