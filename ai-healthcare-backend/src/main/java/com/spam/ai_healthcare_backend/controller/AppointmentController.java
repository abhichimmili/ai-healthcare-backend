package com.spam.ai_healthcare_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.spam.ai_healthcare_backend.dto.AppointmentRequest;
import com.spam.ai_healthcare_backend.dto.AppointmentResponse;
import com.spam.ai_healthcare_backend.dto.DoctorOptionDTO;
import com.spam.ai_healthcare_backend.model.Appointment;
import com.spam.ai_healthcare_backend.model.Doctor;
import com.spam.ai_healthcare_backend.model.Patient;
import com.spam.ai_healthcare_backend.repository.AppointmentRepository;
import com.spam.ai_healthcare_backend.repository.DoctorRepository;
import com.spam.ai_healthcare_backend.repository.PatientRepository;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:5173")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // Patient: get all available doctors
    @GetMapping("/doctors")
    @PreAuthorize("hasRole('PATIENT')")
    public List<DoctorOptionDTO> getDoctors() {

        return doctorRepository.findAll()
                .stream()
                .map(doctor -> new DoctorOptionDTO(
                        doctor.getId(),
                        doctor.getUser().getName(),
                        doctor.getSpecialization()
                ))
                .toList();
    }

    // Patient: get only their appointments
    @GetMapping("/my")
    @PreAuthorize("hasRole('PATIENT')")
    public List<AppointmentResponse> getMyAppointments(
            Authentication authentication) {

        Patient patient = patientRepository
                .findByUserEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Patient profile not found"));

        return appointmentRepository
                .findByPatientIdOrderByAppointmentDateDesc(patient.getId())
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Doctor: get only appointments assigned to them
    @GetMapping("/doctor")
    @PreAuthorize("hasRole('DOCTOR')")
    public List<AppointmentResponse> getDoctorAppointments(
            Authentication authentication) {

        Doctor doctor = doctorRepository
                .findByUserEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Doctor profile not found"));

        return appointmentRepository
                .findByDoctorIdOrderByAppointmentDateDesc(doctor.getId())
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Patient: book an appointment
    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public AppointmentResponse bookAppointment(
            @RequestBody AppointmentRequest request,
            Authentication authentication) {

        Patient patient = patientRepository
                .findByUserEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Patient profile not found"));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Doctor not found"));

        if (request.getAppointmentDate() == null
                || !request.getAppointmentDate().isAfter(
                        java.time.LocalDateTime.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please select a future appointment date and time");
        }

        Appointment appointment = new Appointment();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setReason(request.getReason());
        appointment.setStatus("BOOKED");

        Appointment savedAppointment =
                appointmentRepository.save(appointment);

        return convertToResponse(savedAppointment);
    }

    // Patient: cancel their own appointment
    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('PATIENT')")
    public AppointmentResponse cancelAppointment(
            @PathVariable Long id,
            Authentication authentication) {

        Patient patient = patientRepository
                .findByUserEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Patient profile not found"));

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Appointment not found"));

        if (!appointment.getPatient().getId().equals(patient.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You cannot cancel this appointment");
        }

        appointment.setStatus("CANCELLED");

        return convertToResponse(appointmentRepository.save(appointment));
    }

    // Doctor: accept or reject an appointment
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('DOCTOR')")
    public AppointmentResponse updateAppointmentStatus(
            @PathVariable Long id,
            @RequestParam String status,
            Authentication authentication) {

        Doctor doctor = doctorRepository
                .findByUserEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Doctor profile not found"));

        if (!status.equals("CONFIRMED") && !status.equals("REJECTED")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid appointment status");
        }

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Appointment not found"));

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "This appointment is not assigned to you");
        }

        appointment.setStatus(status);

        return convertToResponse(appointmentRepository.save(appointment));
    }

    private AppointmentResponse convertToResponse(Appointment appointment) {

        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatient().getUser().getName(),
                appointment.getPatient().getUser().getEmail(),
                appointment.getDoctor().getUser().getName(),
                appointment.getDoctor().getSpecialization(),
                appointment.getAppointmentDate(),
                appointment.getReason(),
                appointment.getStatus()
        );
    }
}