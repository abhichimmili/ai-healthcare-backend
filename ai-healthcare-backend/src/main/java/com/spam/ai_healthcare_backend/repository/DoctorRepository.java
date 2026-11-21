
package com.spam.ai_healthcare_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spam.ai_healthcare_backend.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    boolean existsByMedicalLicenseNumber(String medicalLicenseNumber);
    Optional<Doctor> findByUserEmail(String email);

}
