package com.spam.ai_healthcare_backend.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "medical_history")
@Data
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private String conditionName;

    @Column(length = 50)
    private String category;

    @Column(name = "doctor_or_hospital", length = 150)
    private String doctorOrHospital;

    @Column(length = 2000)
    private String description;

    private LocalDate diagnosisDate;

    private String treatment;

    private String allergies;

}