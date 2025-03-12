package dev.markusssh.dagebot.telegram;

import io.github.natanimn.BotClient;
import io.github.natanimn.Webhook;
import io.github.natanimn.enums.ParseMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class TelegramBotConfig {

    @Value("${telegram.token}")
    private String botToken;

    @Value("${telegram.username}")
    private String botUsername;

    @Value("${telegram.use-webhook:false}")
    private boolean useWebhook;

    @Value("${telegram.webhook.url:}")
    private String webhookUrl;

    @Value("${telegram.webhook.port:8443}")
    private int webhookPort;

    @Value("${telegram.webhook.path:/telegram/webhook}")
    private String webhookPath;

    @Value("${telegram.max-activities:3}")
    private int maxActivities;

    @Bean
    public BotClient botClient() {
        BotClient.Builder builder = new BotClient.Builder(botToken)
                .parseMode(ParseMode.HTML)
                .log(true)
                .skipOldUpdates(true);

        return builder.build();
    }

    @Bean
    @Conditional(UseWebhookCondition.class)
    public Webhook telegramWebhook() {
        if (useWebhook) {
            return new Webhook(webhookUrl, webhookPath, webhookPort);
        }
        return null;
    }

    @Bean
    public ExecutorService botExecutorService() {
        return Executors.newSingleThreadExecutor();
    }

    public String getBotUsername() {
        return botUsername;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}

