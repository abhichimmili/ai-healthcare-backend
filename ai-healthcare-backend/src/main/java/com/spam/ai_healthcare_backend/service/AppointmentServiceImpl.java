package com.spam.ai_healthcare_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spam.ai_healthcare_backend.model.Appointment;
import com.spam.ai_healthcare_backend.repository.AppointmentRepository;
import com.spam.ai_healthcare_backend.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment bookAppointment(Appointment appointment) {
        appointment.setStatus("BOOKED");
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @Override
    public Appointment updateAppointment(Long id, Appointment appointment) {

        Appointment existingAppointment = appointmentRepository.findById(id).orElse(null);

        if (existingAppointment != null) {
            existingAppointment.setAppointmentDate(appointment.getAppointmentDate());
            existingAppointment.setDoctor(appointment.getDoctor());
            existingAppointment.setPatient(appointment.getPatient());
            existingAppointment.setReason(appointment.getReason());
            existingAppointment.setStatus(appointment.getStatus());

            return appointmentRepository.save(existingAppointment);
        }

        return null;
    }

    @Override
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);

        if (appointment != null) {
            appointment.setStatus("CANCELLED");
            appointmentRepository.save(appointment);
        }
    }
}