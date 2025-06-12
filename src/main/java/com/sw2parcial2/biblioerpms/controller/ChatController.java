package com.sw2parcial2.biblioerpms.controller;

import com.sw2parcial2.biblioerpms.dto.ChatMessageDTO;
import com.sw2parcial2.biblioerpms.dto.input.ChatInput;
import com.sw2parcial2.biblioerpms.service.DialogflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final DialogflowService dialogflowService;

    @MutationMapping
    public ChatMessageDTO sendChatMessage(@Argument ChatInput input) {
        try {
            log.info("📱 Mensaje recibido del móvil: '{}'", input.getMessage());

            // Enviar a Dialogflow
            ChatMessageDTO response = dialogflowService.detectIntent(
                    input.getMessage(),
                    input.getUserId()
            );

            log.info("📤 Respuesta enviada al móvil: '{}'", response.getMessage());
            log.info("🎯 Acción detectada: '{}'", response.getAction());

            return response;

        } catch (Exception e) {
            log.error("❌ Error procesando mensaje: {}", e.getMessage());

            return ChatMessageDTO.builder()
                    .message("Lo siento, ocurrió un error. ¿Podrías intentarlo de nuevo?")
                    .intent("Error")
                    .action("ERROR")
                    .confidence(0.0)
                    .success(false)
                    .build();
        }
    }
}