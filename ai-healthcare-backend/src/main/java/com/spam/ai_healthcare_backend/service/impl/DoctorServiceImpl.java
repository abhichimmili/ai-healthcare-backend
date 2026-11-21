package com.spam.ai_healthcare_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spam.ai_healthcare_backend.model.Doctor;
import com.spam.ai_healthcare_backend.repository.DoctorRepository;
import com.spam.ai_healthcare_backend.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @Override
    public Doctor updateDoctor(Long id, Doctor doctor) {
        Doctor existingDoctor = doctorRepository.findById(id).orElse(null);

        if (existingDoctor != null) {
            existingDoctor.setSpecialization(doctor.getSpecialization());
            existingDoctor.setQualification(doctor.getQualification());
            existingDoctor.setMedicalLicenseNumber(doctor.getMedicalLicenseNumber());
            existingDoctor.setYearsOfExperience(doctor.getYearsOfExperience());
            existingDoctor.setConsultationFee(doctor.getConsultationFee());

            return doctorRepository.save(existingDoctor);
        }

        return null;
    }
}