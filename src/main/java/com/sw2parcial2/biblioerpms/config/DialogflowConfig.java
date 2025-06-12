package com.sw2parcial2.biblioerpms.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Slf4j
public class DialogflowConfig {

    @Value("${dialogflow.project-id}")
    private String projectId;

    @Value("${dialogflow.credentials-path}")
    private String credentialsPath;

    @Bean
    public SessionsClient sessionsClient() throws IOException {
        try {
            GoogleCredentials credentials = GoogleCredentials
                    .fromStream(new FileInputStream(credentialsPath));

            SessionsSettings settings = SessionsSettings.newBuilder()
                    .setCredentialsProvider(() -> credentials)
                    .build();

            log.info("✅ Dialogflow SessionsClient configurado correctamente");
            return SessionsClient.create(settings);
        } catch (Exception e) {
            log.error("❌ Error configurando Dialogflow: {}", e.getMessage());
            throw e;
        }
    }

    @Bean
    public String dialogflowProjectId() {
        return projectId;
    }
}