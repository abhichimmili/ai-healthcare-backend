package com.spam.ai_healthcare_backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.spam.ai_healthcare_backend.dto.HealthGuidanceRequest;
import com.spam.ai_healthcare_backend.dto.HealthGuidanceResponse;

@Service
public class HealthGuidanceService {
	private static final String DISCLAIMER = "Educational information only. This is not a diagnosis or treatment recommendation. Contact a licensed healthcare professional for personal medical advice.";
	private final RestClient restClient = RestClient.builder().build();
	private final String apiKey;
	private final String apiUrl;
	private final String model;

	public HealthGuidanceService(@Value("${ai.api.key:}") String apiKey, @Value("${ai.api.url}") String apiUrl,
			@Value("${ai.api.model}") String model) {
		this.apiKey = apiKey;
		this.apiUrl = apiUrl;
		this.model = model;
	}

	@SuppressWarnings("unchecked")
	public HealthGuidanceResponse getGuidance(HealthGuidanceRequest request) {
		String symptoms = request.getSymptoms() == null ? "" : request.getSymptoms().trim();
		if (symptoms.isBlank() || symptoms.length() > 3000)
			throw new IllegalArgumentException("Symptoms are required and must be 3000 characters or fewer.");
		if (apiKey == null || apiKey.isBlank())
			throw new IllegalStateException("AI service is not configured. Set GROQ_API_KEY on the backend.");

		String systemPrompt = "You are a cautious healthcare information assistant. Do not diagnose, prescribe, recommend medication dosages, or claim certainty. Provide general educational guidance, explain uncertainty, and advise professional assessment. If symptoms suggest an emergency (chest pain, severe breathing difficulty, stroke signs, unconsciousness, seizure, severe bleeding), clearly advise emergency care now. Use simple language.";
		String userPrompt = "Symptoms: " + symptoms + "\nDuration: " + safe(request.getDuration())
				+ "\nSelf-reported severity: " + safe(request.getSeverity())
				+ "\nGive concise general educational guidance. Do not infer missing details.";
		Map<String, Object> payload = Map.of("model", model, "temperature", 0.2, "max_tokens", 500, "messages", List
				.of(Map.of("role", "system", "content", systemPrompt), Map.of("role", "user", "content", userPrompt)));
		try {
			Map<String, Object> response = restClient.post().uri(apiUrl).contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", "Bearer " + apiKey).body(payload).retrieve().body(Map.class);
			if (response == null || !(response.get("choices") instanceof List<?> choices) || choices.isEmpty())
				throw new IllegalStateException("AI provider returned an empty response.");
			Map<String, Object> first = (Map<String, Object>) choices.get(0);
			Map<String, Object> message = (Map<String, Object>) first.get("message");
			String content = message == null ? null : String.valueOf(message.get("content"));
			if (content == null || content.isBlank())
				throw new IllegalStateException("AI provider returned no guidance text.");
			return new HealthGuidanceResponse(content, DISCLAIMER, hasEmergencySignal(symptoms));
		} catch (RestClientException ex) {
			throw new IllegalStateException("The AI service is temporarily unavailable. Please try again later.", ex);
		}
	}

	private String safe(String value) {
		return value == null || value.isBlank() ? "Not provided" : value.trim();
	}

	private boolean hasEmergencySignal(String value) {
		String text = value.toLowerCase();
		return text.contains("chest pain") || text.contains("difficulty breathing")
				|| text.contains("shortness of breath") || text.contains("cannot breathe")
				|| text.contains("unconscious") || text.contains("severe bleeding") || text.contains("stroke")
				|| text.contains("seizure");
	}
}
