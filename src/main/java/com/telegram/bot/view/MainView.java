package com.telegram.bot.view;

import com.telegram.bot.view.util.MainStage;
import com.telegram.bot.view.util.ViewFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MainView implements BotView {
    private ViewFactory vf = new ViewFactory();
    private MainStage stage;

    public MainView(MainStage stage) {
        this.stage = stage;
    }

    @Override
    public BotApiMethod<?> render(Update update) {
        switch (stage) {
            case sendMsg:
                return vf.createSendMsg(update, "какой-то текст");
        }
        return null;
    }
}
