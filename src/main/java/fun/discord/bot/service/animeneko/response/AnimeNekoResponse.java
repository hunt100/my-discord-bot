package fun.discord.bot.service.animeneko.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimeNekoResponse {
    private Integer code;
    private String url;
    private String message;
}
