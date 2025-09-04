package com.wipro.komal.patient.service;

import com.wipro.komal.patient.entity.Patient;
import com.wipro.komal.patient.exception.PatientNotFoundException;
import com.wipro.komal.patient.repo.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

  private final PatientRepository repo;

  @Override
  public Patient addPatient(Patient p){
    try {
      return repo.save(p);
    } catch (Exception ex) {
      throw ex;
    }
  }

  @Override
  public Page<Patient> list(int page, int size){
    return repo.findAll(PageRequest.of(page, size));
  }

  @Override
  public Patient get(Long id){
    return repo.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient " + id + " not found"));
  }

  @Override
  public Patient update(Long id, Patient p){
    Patient cur = get(id);
    p.setId(cur.getId());
    return repo.save(p);
  }

  @Override
  public void delete(Long id){
    Patient cur = get(id);
    repo.delete(cur);
  }
}
