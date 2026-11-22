package com.spam.ai_healthcare_backend.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfig {

    @Bean
    public Path medicalReportStoragePath(
            @Value("${app.file.upload-dir:uploads/medical-reports}")
            String uploadDirectory) {

        Path path = Paths.get(uploadDirectory)
                .toAbsolutePath()
                .normalize();

        try {
            java.nio.file.Files.createDirectories(path);
        } catch (java.io.IOException e) {
            throw new IllegalStateException(
                    "Could not create medical report storage directory", e
            );
        }

        return path;
    }
}