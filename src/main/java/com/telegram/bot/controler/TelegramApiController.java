package com.telegram.bot.controler;

import com.telegram.bot.model.DTO.SendMessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/telegram")
public class TelegramApiController {
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody SendMessageDTO msg) {
        return null;
    }
}
