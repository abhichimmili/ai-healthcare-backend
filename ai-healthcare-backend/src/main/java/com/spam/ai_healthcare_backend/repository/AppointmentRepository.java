package com.spam.ai_healthcare_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;

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

    @Query("select count(distinct a.patient.id) from Appointment a where a.doctor.id = :doctorId")
    long countDistinctPatientsByDoctorId(@Param("doctorId") Long doctorId);

    @Query("select a from Appointment a where a.patient.id = :patientId and a.appointmentDate >= :now and a.status <> 'CANCELLED' order by a.appointmentDate asc")
    List<Appointment> findUpcomingAppointments(
            @Param("patientId") Long patientId,
            @Param("now") LocalDateTime now);

}