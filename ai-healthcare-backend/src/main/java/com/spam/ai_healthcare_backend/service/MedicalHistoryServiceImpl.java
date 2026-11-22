package com.spam.ai_healthcare_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spam.ai_healthcare_backend.dto.MedicalHistoryRequest;
import com.spam.ai_healthcare_backend.dto.MedicalHistoryResponse;
import com.spam.ai_healthcare_backend.model.MedicalHistory;
import com.spam.ai_healthcare_backend.model.Patient;
import com.spam.ai_healthcare_backend.repository.MedicalHistoryRepository;
import com.spam.ai_healthcare_backend.repository.PatientRepository;
import com.spam.ai_healthcare_backend.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    private final MedicalHistoryRepository historyRepository;
    private final PatientRepository patientRepository;

    @Override
    public MedicalHistoryResponse addMedicalHistory(
            String email,
            MedicalHistoryRequest request) {

        Patient patient = getPatient(email);

        MedicalHistory history = new MedicalHistory();

        history.setPatient(patient);
        history.setConditionName(request.getConditionName());
        history.setCategory(request.getCategory());
        history.setDoctorOrHospital(request.getDoctorOrHospital());
        history.setDescription(request.getDescription());
        history.setDiagnosisDate(request.getDiagnosisDate());
        history.setTreatment(request.getTreatment());
        history.setAllergies(request.getAllergies());

        return convertToResponse(historyRepository.save(history));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalHistoryResponse> getMyMedicalHistory(String email) {

        return historyRepository
                .findByPatientUserEmailOrderByDiagnosisDateDesc(email)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalHistoryResponse getMedicalHistoryById(
            Long id,
            String email) {

        MedicalHistory history = getOwnedHistory(id, email);

        return convertToResponse(history);
    }

    @Override
    public MedicalHistoryResponse updateMedicalHistory(
            Long id,
            String email,
            MedicalHistoryRequest request) {

        MedicalHistory history = getOwnedHistory(id, email);

        history.setConditionName(request.getConditionName());
        history.setCategory(request.getCategory());
        history.setDoctorOrHospital(request.getDoctorOrHospital());
        history.setDescription(request.getDescription());
        history.setDiagnosisDate(request.getDiagnosisDate());
        history.setTreatment(request.getTreatment());
        history.setAllergies(request.getAllergies());

        return convertToResponse(historyRepository.save(history));
    }

    @Override
    public void deleteMedicalHistory(Long id, String email) {

        MedicalHistory history = getOwnedHistory(id, email);

        historyRepository.delete(history);
    }

    private Patient getPatient(String email) {

        return patientRepository.findByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Patient profile not found"));
    }

    private MedicalHistory getOwnedHistory(Long id, String email) {

        MedicalHistory history = historyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical history not found"));

        if (!history.getPatient().getUser().getEmail().equals(email)) {
            throw new ResourceNotFoundException("Medical history not found");
        }

        return history;
    }

    private MedicalHistoryResponse convertToResponse(
            MedicalHistory history) {

        return new MedicalHistoryResponse(
                history.getId(),
                history.getConditionName(),
                history.getCategory(),
                history.getDoctorOrHospital(),
                history.getDescription(),
                history.getDiagnosisDate(),
                history.getTreatment(),
                history.getAllergies()
        );
    }
}