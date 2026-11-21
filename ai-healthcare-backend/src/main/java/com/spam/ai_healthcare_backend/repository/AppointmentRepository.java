package com.spam.ai_healthcare_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spam.ai_healthcare_backend.model.Appointment;

@Repository
public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    long countByPatientId(Long patientId);

    long countByPatientIdAndAppointmentDateAfterAndStatusNot(
            Long patientId,
            LocalDateTime date,
            String status
    );

    long countByDoctorIdAndAppointmentDateBetween(
            Long doctorId,
            LocalDateTime start,
            LocalDateTime end
    );

    long countByDoctorIdAndStatus(
            Long doctorId,
            String status
    );

    @Query("""
        SELECT a FROM Appointment a
        JOIN FETCH a.doctor d
        JOIN FETCH d.user
        WHERE a.patient.id = :patientId
        AND a.appointmentDate > :now
        AND a.status <> 'CANCELLED'
        ORDER BY a.appointmentDate ASC
    """)
    List<Appointment> findUpcomingAppointments(
            @Param("patientId") Long patientId,
            @Param("now") LocalDateTime now
    );

    @Query("""
        SELECT COUNT(DISTINCT a.patient.id)
        FROM Appointment a
        WHERE a.doctor.id = :doctorId
    """)
    long countDistinctPatientsByDoctorId(
            @Param("doctorId") Long doctorId
    );

}