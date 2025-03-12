package dev.markusssh.dagebot.telegram;

import io.github.natanimn.BotContext;
import io.github.natanimn.types.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {

    private final MessageSource messageSource;

    @Autowired
    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }

    public void handleStartCommand(BotContext context, Message message) {
        String replyMessage;
        switch (message.chat.type) {
            case ChatTypes.PRIVATE -> replyMessage = getMessage("bot.start.private");
            case ChatTypes.GROUP -> replyMessage = getMessage("bot.start.group");
            default -> replyMessage = getMessage("bot.start.unknown");
        }
        context.reply(replyMessage).exec();
    }

    public void handleNewActivityCommand(BotContext context, Message message) {
        String replyMessage;
        switch (message.chat.type) {
            case ChatTypes.PRIVATE -> replyMessage = getMessage("bot.new-activity.private", "имя");
            case ChatTypes.GROUP -> replyMessage = getMessage("bot.new-activity.group");
            default -> replyMessage = getMessage("bot.new-activity.unknown");
        }
        context.reply(replyMessage).exec();
    }

}
