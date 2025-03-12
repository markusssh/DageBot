package dev.markusssh.dagebot.telegram.ui;

import dev.markusssh.dagebot.telegram.TelegramBotConfig;
import io.github.natanimn.types.InlineKeyboardButton;
import io.github.natanimn.types.InlineKeyboardMarkup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyboardService {

    private final TelegramBotConfig config;

    @Autowired
    public KeyboardService(TelegramBotConfig config) {
        this.config = config;
    }

    public InlineKeyboardMarkup getStartKeyboardMarkup() {
        String botUsername = config.getBotUsername();

        var button = new InlineKeyboardButton("⌛Активности");
        button.callback_data = "activity";

        var markup = new InlineKeyboardMarkup();
        markup.addKeyboard(button);

        return markup;
    }

}
