
package com.spam.ai_healthcare_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spam.ai_healthcare_backend.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
