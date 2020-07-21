package fun.discord.bot.config;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@Configuration
public class InitialJDAStartupConfig {

    private final JPAListener listener;

    @Value("${discord.token}")
    private String discordToken;

    @Autowired
    public InitialJDAStartupConfig(JPAListener listener) {
        this.listener = listener;
    }


    @Bean
    public Object jda() throws LoginException {
        return new JDABuilder()
                .setToken(discordToken)
                .addEventListeners(listener)
                .setActivity(Activity.watching(1337 + " °."))
                .build();
    }
}
