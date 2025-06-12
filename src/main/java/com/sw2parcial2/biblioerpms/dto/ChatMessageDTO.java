// src/main/java/com/sw2parcial2/biblioerpms/dto/ChatMessageDTO.java
package com.sw2parcial2.biblioerpms.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ChatMessageDTO {
    private String message;           // Respuesta para mostrar al usuario
    private String intent;           // Intent detectado
    private String action;           // Acción a ejecutar en la app
    private Double confidence;       // Nivel de confianza
    private Map<String, Object> parameters; // Parámetros extraídos
    private boolean success;         // Si la operación fue exitosa
}