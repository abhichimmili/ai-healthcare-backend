
package com.spam.ai_healthcare_backend.service;

import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spam.ai_healthcare_backend.dto.DoctorRegistrationRequest;

import com.spam.ai_healthcare_backend.model.User;
import com.spam.ai_healthcare_backend.model.Doctor;

import com.spam.ai_healthcare_backend.repository.UserRepository;
import com.spam.ai_healthcare_backend.repository.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String registerDoctor(DoctorRegistrationRequest request) {

        // Check whether email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Check password and confirm password
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // Check duplicate medical license
        if (doctorRepository.existsByMedicalLicenseNumber(
                request.getMedicalLicenseNumber())) {

            throw new RuntimeException("Medical license already registered");
        }

        // Create User object
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("DOCTOR");
        user.setCreatedAt(LocalDateTime.now());

        // Save User details
        User savedUser = userRepository.save(user);

        // Create Doctor object
        Doctor doctor = new Doctor();

        doctor.setUser(savedUser);
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setMedicalLicenseNumber(
                request.getMedicalLicenseNumber());
        doctor.setYearsOfExperience(request.getYearsOfExperience());
        doctor.setConsultationFee(request.getConsultationFee());

        // Save Doctor details
        doctorRepository.save(doctor);

        return "Doctor registered successfully";
    }
}
