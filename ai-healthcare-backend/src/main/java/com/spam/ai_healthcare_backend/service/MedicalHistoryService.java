package com.spam.ai_healthcare_backend.service;

import java.util.List;

import com.spam.ai_healthcare_backend.dto.MedicalHistoryRequest;
import com.spam.ai_healthcare_backend.dto.MedicalHistoryResponse;

public interface MedicalHistoryService {

	MedicalHistoryResponse addMedicalHistory(String email, MedicalHistoryRequest request);

	List<MedicalHistoryResponse> getMyMedicalHistory(String email);

	MedicalHistoryResponse getMedicalHistoryById(Long id, String email);

	MedicalHistoryResponse updateMedicalHistory(Long id, String email, MedicalHistoryRequest request);

	void deleteMedicalHistory(Long id, String email);
}