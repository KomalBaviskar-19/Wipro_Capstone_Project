package com.wipro.komal.patient.service;

import com.wipro.komal.patient.entity.Patient;
import org.springframework.data.domain.Page;

public interface PatientService {
  Patient addPatient(Patient p);
  Page<Patient> list(int page, int size);
  Patient get(Long id);
  Patient update(Long id, Patient p);
  void delete(Long id);
}
