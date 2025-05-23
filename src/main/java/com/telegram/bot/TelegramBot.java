package com.telegram.bot;

import com.telegram.bot.controler.util.BotDispatcher;
import com.telegram.bot.view.BotView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.username}")
    private String botUsername;
    private final BotDispatcher dispatcher;

    @Autowired
    public TelegramBot(@Value("${bot.token}") String botToken, BotDispatcher dispatcher) {
        super(botToken);
        this.dispatcher = dispatcher;
    }

    @Override
    public void onUpdateReceived(Update update) {
        BotView view = dispatcher.dispatch(update);
        if (view != null) {
            BotApiMethod<?> method = view.render(update);
            try {
                execute(method);
            } catch (TelegramApiException e) {
                log.error("Не удалось отправить сообщение: " + e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
