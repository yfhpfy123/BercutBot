package com.telegram.bot.view.util;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class ViewFactory {
    public InlineKeyboardButton createBtn(String label, String callback) {
        InlineKeyboardButton btn = new InlineKeyboardButton(label);
        btn.setCallbackData(callback);
        return btn;
    }

    public SendMessage createSendMsg(Update update, String text) {
        String chatId = update.getMessage().getChatId().toString();
        return new SendMessage(chatId, text);
    }

    public SendMessage createSendMsg(Update update, String text, InlineKeyboardButton btn) {
        SendMessage msg = createSendMsg(update, text);
        msg.setReplyMarkup(createKeyboardMarkup(List.of(btn)));
        return msg;
    }

    public SendMessage createSendMsg(Update update, String text, List<InlineKeyboardButton> btnsList) {
        SendMessage msg = createSendMsg(update, text);
        msg.setReplyMarkup(createKeyboardMarkup(btnsList));
        return msg;
    }

    public InlineKeyboardMarkup createKeyboardMarkup(List<InlineKeyboardButton> btnsList) {
        List<List<InlineKeyboardButton>> raws = new ArrayList<>();
        for (InlineKeyboardButton btn : btnsList) {
            raws.add(List.of(btn));
        }
        return new InlineKeyboardMarkup(raws);
    }

    public EditMessageText createEditMsg(Update update, String text) {
        EditMessageText msg = new EditMessageText();
        msg.setChatId(update.getCallbackQuery().getMessage().getChatId());
        msg.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        msg.setText(text);

        return msg;
    }

    public EditMessageText createEditMsg(Update update, String text, InlineKeyboardButton btn) {
        EditMessageText msg = createEditMsg(update, text);
        msg.setReplyMarkup(createKeyboardMarkup(List.of(btn)));

        return msg;
    }

    public EditMessageText createEditMsg(Update update, String text, List<InlineKeyboardButton> btnList) {
        EditMessageText msg = createEditMsg(update, text);
        msg.setReplyMarkup(createKeyboardMarkup(btnList));

        return msg;
    }




}
