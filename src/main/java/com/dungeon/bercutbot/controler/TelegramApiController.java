package com.dungeon.bercutbot.controler;

import com.dungeon.bercutbot.model.DTO.SendMessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@RestController
@RequestMapping("/api/telegram")
public class TelegramApiController {
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@ResponseBody SendMessageDTO msg) {
        return null;
    }
}
