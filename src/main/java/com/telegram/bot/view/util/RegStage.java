package com.telegram.bot.view.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RegStage {
    start("Для продолженя необходима регистрация"),
    login("Введите логин с помощью команды /login\n\nнапример: \n/login/example@ex.ru"),
    password("Введите пароль с помощью команды /pass\\n\\nнапример: \\n/pass/password123");

    private final String desc;

}
