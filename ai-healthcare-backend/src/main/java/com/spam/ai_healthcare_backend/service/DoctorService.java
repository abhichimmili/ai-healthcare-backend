package com.spam.ai_healthcare_backend.service;

import java.util.List;
import com.spam.ai_healthcare_backend.model.Doctor;

public interface DoctorService {

    Doctor saveDoctor(Doctor doctor);

    List<Doctor> getAllDoctors();

    Doctor getDoctorById(Long id);

    Doctor updateDoctor(Long id, Doctor doctor);
}