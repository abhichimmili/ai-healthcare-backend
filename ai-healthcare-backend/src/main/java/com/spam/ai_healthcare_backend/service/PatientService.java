
package com.spam.ai_healthcare_backend.service;

import com.spam.ai_healthcare_backend.dto.PatientRegistrationRequest;

public interface PatientService {

    String registerPatient(PatientRegistrationRequest request);

}
