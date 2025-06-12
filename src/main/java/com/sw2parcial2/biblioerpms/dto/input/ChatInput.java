// src/main/java/com/sw2parcial2/biblioerpms/dto/input/ChatInput.java
package com.sw2parcial2.biblioerpms.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatInput {
    @NotBlank
    private String message;

    private String userId; // Opcional, para mantener sesión
}