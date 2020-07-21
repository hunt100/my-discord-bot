package fun.discord.bot.command.commandImpl;

import fun.discord.bot.command.Command;
import fun.discord.bot.command.CommandContext;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

@Component
@Profile("prod")
@Slf4j
public class PinghCommand implements Command {
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        if (args.size() < 1) {
            channel.sendMessage(":x: Не хватает аргумента(-ов). Воспользуйтесь b!help pingh").queue();
            return;
        }

//        boolean isAvailable = pingHost(args.get(0), Integer.parseInt(args.get(1)), 50000);
        String hostWithHttp = args.get(0).contains("http://") || args.get(0).contains("https://") ? args.get(0) : "http://" + args.get(0);
        boolean isAvailable = pingDNSHost(hostWithHttp);
        String availableText = isAvailable ? "доступен" : "не доступен";
        channel.sendMessageFormat("Сайт: %s " + availableText, hostWithHttp).queue();
    }

    @Override
    public String getName() {
        return "pingh";
    }

    @Override
    public String getHelp() {
        return "Пытается отправить GET-запрос на указанный сайт\n" +
                "При ответе 200, сайт доступен\n" +
                "Использование: `b!pingh [сайт]`";
    }

    private boolean pingHost(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }

    private boolean pingDNSHost(String host) {
        try(CloseableHttpClient httpclient = HttpClients.createDefault();) {
            HttpGet request = new HttpGet(host);
            log.info("Executing request " + request.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(request);
            final int status = response.getStatusLine().getStatusCode();
            return status >= 200 && status < 300;
        } catch (Exception e) {
            log.error("Error: ", e);
            return false;
        }
    }
}
