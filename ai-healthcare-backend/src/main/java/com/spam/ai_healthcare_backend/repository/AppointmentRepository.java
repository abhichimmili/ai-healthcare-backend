package com.spam.ai_healthcare_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spam.ai_healthcare_backend.model.Appointment;

@Repository
public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    long countByPatientId(Long patientId);

    long countByPatientIdAndAppointmentDateAfterAndStatusNot(
            Long patientId,
            java.time.LocalDateTime date,
            String status
    );

    long countByDoctorIdAndAppointmentDateBetween(
            Long doctorId,
            java.time.LocalDateTime start,
            java.time.LocalDateTime end
    );

    long countByDoctorIdAndStatus(Long doctorId, String status);

    List<Appointment> findByPatientIdOrderByAppointmentDateDesc(Long patientId);

    List<Appointment> findByDoctorIdOrderByAppointmentDateDesc(Long doctorId);
}