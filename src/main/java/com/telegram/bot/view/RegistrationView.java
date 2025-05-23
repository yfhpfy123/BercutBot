package com.telegram.bot.view;

import com.telegram.bot.view.util.RegStage;
import com.telegram.bot.view.util.ViewFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RegistrationView implements BotView{
    private ViewFactory vf = new ViewFactory();
    private RegStage stage;

    public RegistrationView(RegStage stage) {
        this.stage = stage;
    }

    @Override
    public BotApiMethod<?> render(Update update) {
        switch (stage) {
            case start:
                return vf.createSendMsg(
                        update,
                        stage.getDesc(),
                        vf.createBtn("регистрация", String.format("/reg/{%s}", RegStage.login))
                );
            case login:
                return vf.createEditMsg(update, stage.getDesc());
        }
        return null;
    }
}
