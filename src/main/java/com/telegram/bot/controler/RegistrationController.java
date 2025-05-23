package com.telegram.bot.controler;

import com.telegram.bot.annotation.BotCommand;
import com.telegram.bot.annotation.BotController;
import com.telegram.bot.annotation.BotRequestMapping;
import com.telegram.bot.annotation.BotRequestParam;
import com.telegram.bot.view.BotView;
import com.telegram.bot.view.MainView;
import com.telegram.bot.view.RegistrationView;
import com.telegram.bot.view.util.MainStage;
import com.telegram.bot.view.util.RegStage;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@BotController
public class RegistrationController {

    @BotCommand("/start")
    public BotView startBot(Update update) {
        return new RegistrationView(RegStage.start);
    }

    @BotRequestMapping("/reg/{stage}")
    public BotView registration(@BotRequestParam("stage") String stage, Update update) {
        return new RegistrationView(RegStage.valueOf(stage));
    }

    @BotRequestMapping("/login/userData")
    public BotView saveLogin(@BotRequestParam("userData") String login, Update update) {
        log.info(login);
        return new RegistrationView(RegStage.password); // TODO
    }

    @BotRequestMapping("/pass/userData")
    public BotView savePass(@BotRequestParam("userData") String pass, Update update) {
        log.info(pass);
        return new MainView(MainStage.sendMsg); // TODO
    }
}
