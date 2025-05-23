package com.telegram.bot.view;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotView {
    BotApiMethod<?> render(Update update);
}
