
package com.spam.ai_healthcare_backend.service;

import com.spam.ai_healthcare_backend.dto.DoctorRegistrationRequest;

public interface DoctorService {

    String registerDoctor(DoctorRegistrationRequest request);

}
