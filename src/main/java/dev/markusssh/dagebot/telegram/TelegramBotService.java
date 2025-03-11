package dev.markusssh.dagebot.telegram;

import io.github.natanimn.BotClient;
import io.github.natanimn.Webhook;
import io.github.natanimn.filters.Filter;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

@Service
public class TelegramBotService {
    private final BotClient botClient;
    private final ExecutorService executorService;
    private final Optional<Webhook> webhook;
    private final boolean useWebhook;
    private final ConfigurableApplicationContext applicationContext;

    @Autowired
    public TelegramBotService(BotClient botClient,
                              ExecutorService executorService,
                              Optional<Webhook> webhook,
                              @Value("${telegram.bot.use-webhook:false}") boolean useWebhook,
                              ConfigurableApplicationContext applicationContext) {
        this.botClient = botClient;
        this.executorService = executorService;
        this.webhook = webhook;
        this.useWebhook = useWebhook;
        this.applicationContext = applicationContext;

        setupMessageHandlers();
    }

    private void setupMessageHandlers() {
        botClient.onMessage(
                filter -> filter.commands("start"), (context, _) -> context.reply("Добро пожаловать!").exec());

        botClient.onMessage(Filter::text, (context, message) -> {
                    String response = "Вы сказали: " + message.text;
                    context.reply(response).exec();
                }
        );
    }

    @EventListener(ContextRefreshedEvent.class)
    public void startBot() {
        if (useWebhook && webhook.isPresent()) {
            botClient.setWebhook(webhook.get());
        }

        executorService.execute(() -> {
            try {
                botClient.run();
            } catch (Exception e) {
                System.err.println("Ошибка при запуске бота: " + e.getMessage());
                applicationContext.close();
            }
        });
    }

    @PreDestroy
    public void stopBot() {
        botClient.stop();
        executorService.shutdownNow();
    }


}
