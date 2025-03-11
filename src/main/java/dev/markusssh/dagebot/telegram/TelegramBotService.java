package dev.markusssh.dagebot.telegram;

import io.github.natanimn.BotClient;
import io.github.natanimn.Webhook;
import io.github.natanimn.filters.Filter;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(TelegramBotService.class);

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
                filter -> filter.commands("start"), (context, _) -> context.reply("Я люблю тебя, Софа!❤️❤️").exec());

        botClient.onMessage(Filter::text, (context, message) -> {
                    String response = "Вы сказали: " + message.text;
                    context.reply(response).exec();
                }
        );
    }

    @EventListener(ContextRefreshedEvent.class)
    public void startBot() {
        if (executorService.isShutdown() || executorService.isTerminated()) {
            return;
        }

        if (useWebhook && webhook.isPresent()) {
            botClient.setWebhook(webhook.get());
        }

        executorService.execute(() -> {
            try {
                botClient.run();
            } catch (Exception e) {
                if (!Thread.currentThread().isInterrupted() && !executorService.isShutdown()) {
                    logger.error("Error while creating DageBot {}", e.getMessage(), e);
                    applicationContext.close();
                } else {
                    logger.info("DageBot has been terminated");
                }
            }
        });
    }


    @PreDestroy
    public void stopBot() {
        botClient.stop();
        executorService.shutdownNow();
    }


}
