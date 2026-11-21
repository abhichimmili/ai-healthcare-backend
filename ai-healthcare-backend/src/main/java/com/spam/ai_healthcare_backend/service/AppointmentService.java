package com.spam.ai_healthcare_backend.service;

import java.util.List;

import com.spam.ai_healthcare_backend.model.Appointment;

public interface AppointmentService {

    Appointment bookAppointment(Appointment appointment);

    List<Appointment> getAllAppointments();

    Appointment getAppointmentById(Long id);

    Appointment updateAppointment(Long id, Appointment appointment);

    void cancelAppointment(Long id);
}