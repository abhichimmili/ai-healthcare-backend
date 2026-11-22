package com.spam.ai_healthcare_backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.spam.ai_healthcare_backend.dto.MedicalHistoryRequest;
import com.spam.ai_healthcare_backend.dto.MedicalHistoryResponse;
import com.spam.ai_healthcare_backend.service.MedicalHistoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/medical-history")
@RequiredArgsConstructor
@Validated
@PreAuthorize("hasRole('PATIENT')")
public class MedicalHistoryController {

    private final MedicalHistoryService historyService;

    @PostMapping
    public ResponseEntity<MedicalHistoryResponse> addHistory(
            Principal principal,
            @Valid @RequestBody MedicalHistoryRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(historyService.addMedicalHistory(
                        principal.getName(), request
                ));
    }

    @GetMapping
    public ResponseEntity<List<MedicalHistoryResponse>> getMyHistory(
            Principal principal) {

        return ResponseEntity.ok(
                historyService.getMyMedicalHistory(principal.getName())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalHistoryResponse> getHistoryById(
            @PathVariable Long id,
            Principal principal) {

        return ResponseEntity.ok(
                historyService.getMedicalHistoryById(
                        id, principal.getName()
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalHistoryResponse> updateHistory(
            @PathVariable Long id,
            Principal principal,
            @Valid @RequestBody MedicalHistoryRequest request) {

        return ResponseEntity.ok(
                historyService.updateMedicalHistory(
                        id, principal.getName(), request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistory(
            @PathVariable Long id,
            Principal principal) {

        historyService.deleteMedicalHistory(
                id, principal.getName()
        );

        return ResponseEntity.noContent().build();
    }
}