package com.telegram.bot.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@EqualsAndHashCode
public class BotUser {
    @Id
    private Long chatId;
    private String userName;
    private String firstName;
    private String LastName;
}
