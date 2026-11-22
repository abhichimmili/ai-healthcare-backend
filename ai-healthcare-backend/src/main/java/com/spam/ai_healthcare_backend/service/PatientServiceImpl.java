
package com.spam.ai_healthcare_backend.service;

import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spam.ai_healthcare_backend.dto.PatientRegistrationRequest;
import com.spam.ai_healthcare_backend.model.User;
import com.spam.ai_healthcare_backend.model.Patient;
import com.spam.ai_healthcare_backend.repository.UserRepository;
import com.spam.ai_healthcare_backend.repository.PatientRepository;
import com.spam.ai_healthcare_backend.exception.BadRequestException;
import com.spam.ai_healthcare_backend.exception.DuplicateResourceException;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String registerPatient(PatientRegistrationRequest request) {

        // Check whether email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }

        // Check password and confirm password
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        // Create User object
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("PATIENT");
        user.setCreatedAt(LocalDateTime.now());

        // Save User details
        User savedUser = userRepository.save(user);

        // Create Patient object
        Patient patient = new Patient();

        patient.setUser(savedUser);
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setAddress(request.getAddress());

        // Save Patient details
        patientRepository.save(patient);

        return "Patient registered successfully";

    }

}
