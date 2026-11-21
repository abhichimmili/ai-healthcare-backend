package com.spam.ai_healthcare_backend.dto;

public class DoctorOptionDTO {

    private Long id;
    private String name;
    private String specialization;

    public DoctorOptionDTO(Long id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }
}