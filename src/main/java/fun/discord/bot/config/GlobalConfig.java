package fun.discord.bot.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {
    private boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
