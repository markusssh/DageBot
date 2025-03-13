package dev.markusssh.dagebot.telegram;

import dev.markusssh.dagebot.telegram.ui.KeyboardService;
import io.github.natanimn.BotContext;
import io.github.natanimn.types.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);
    private final MessageSource messageSource;
    private final KeyboardService keyboardService;

    @Autowired
    public MessageService(MessageSource messageSource,
                          KeyboardService keyboardService) {
        this.keyboardService = keyboardService;
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
        long chatId = message.chat.id;
        long userId = message.from.id;

        switch (message.chat.type) {
            case ChatTypes.PRIVATE -> context.reply(getMessage("bot.new-activity.from-private")).exec();
            case ChatTypes.GROUP -> {
                try {
                    String placeholderMessage = getMessage("bot.new-activity.from-group.placeholder");
                    String creationFormMessage = getMessage("bot.new-activity.from-group.creation", "имя");
                    context.sendMessage(userId, creationFormMessage).exec();
                    int placeholderMessageId = context.reply(placeholderMessage).exec().message_id;


                } catch (Exception e) {
                    context
                            .reply(getMessage("bot.exception.no-chat"))
                            .replyMarkup(keyboardService.getNoChatExceptionKeyboardMarkup())
                            .exec();
                }
            }
            default -> context.reply(getMessage("bot.new-activity.unknown")).exec();
        }
    }

}
