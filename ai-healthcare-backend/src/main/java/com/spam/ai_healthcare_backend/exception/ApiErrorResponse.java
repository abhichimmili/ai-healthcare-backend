package com.spam.ai_healthcare_backend.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path, Map<String, String> validationErrors) { }
