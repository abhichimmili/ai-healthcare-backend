package com.spam.ai_healthcare_backend.dto;

import lombok.Data;

@Data
public class PatientDashboardDTO {

	
	public PatientDashboardDTO(String patientName, Long totalAppointments, Long upcomingAppointments,
			Long totalMedicalReports, String healthStatus) {
		super();
		this.patientName = patientName;
		this.totalAppointments = totalAppointments;
		this.upcomingAppointments = upcomingAppointments;
		this.totalMedicalReports = totalMedicalReports;
		this.healthStatus = healthStatus;
	}
	private String patientName;
	private Long totalAppointments;
	private Long upcomingAppointments;
	private Long totalMedicalReports;
	private String healthStatus;

}
