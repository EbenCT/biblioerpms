package com.sw2parcial2.biblioerpms.service;

import com.google.cloud.dialogflow.v2.*;
import com.sw2parcial2.biblioerpms.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DialogflowService {

    private final SessionsClient sessionsClient;
    private final String dialogflowProjectId;

    public ChatMessageDTO detectIntent(String message, String userId) {
        try {
            // Crear sesión única para el usuario
            String sessionId = userId != null ? userId : UUID.randomUUID().toString();
            SessionName session = SessionName.of(dialogflowProjectId, sessionId);

            log.info("🤖 Enviando mensaje a Dialogflow: '{}' para sesión: {}", message, sessionId);

            // Crear el input de texto
            TextInput textInput = TextInput.newBuilder()
                    .setText(message)
                    .setLanguageCode("es") // Español
                    .build();

            QueryInput queryInput = QueryInput.newBuilder()
                    .setText(textInput)
                    .build();

            // Detectar intent
            DetectIntentResponse response = sessionsClient.detectIntent(
                    DetectIntentRequest.newBuilder()
                            .setSession(session.toString())
                            .setQueryInput(queryInput)
                            .build()
            );

            // Procesar respuesta
            QueryResult queryResult = response.getQueryResult();

            log.info("📥 Intent detectado: {}", queryResult.getIntent().getDisplayName());
            log.info("📝 Respuesta: {}", queryResult.getFulfillmentText());
            log.info("🎯 Confianza: {}", queryResult.getIntentDetectionConfidence());

            // Extraer parámetros
            Map<String, Object> parameters = new HashMap<>();
            queryResult.getParameters().getFieldsMap().forEach((key, value) -> {
                if (value.hasStringValue()) {
                    parameters.put(key, value.getStringValue());
                } else if (value.hasListValue()) {
                    parameters.put(key, value.getListValue().getValuesList().toString());
                } else {
                    parameters.put(key, value.toString());
                }
            });

            return ChatMessageDTO.builder()
                    .message(queryResult.getFulfillmentText())
                    .intent(queryResult.getIntent().getDisplayName())
                    .action(queryResult.getAction())
                    .confidence((double) queryResult.getIntentDetectionConfidence())
                    .parameters(parameters)
                    .success(true)
                    .build();

        } catch (Exception e) {
            log.error("❌ Error en Dialogflow: {}", e.getMessage());

            // Respuesta de error amigable
            return ChatMessageDTO.builder()
                    .message("Lo siento, no pude procesar tu solicitud. ¿Podrías intentarlo de nuevo?")
                    .intent("Error")
                    .action("ERROR")
                    .confidence(0.0)
                    .parameters(new HashMap<>())
                    .success(false)
                    .build();
        }
    }
}