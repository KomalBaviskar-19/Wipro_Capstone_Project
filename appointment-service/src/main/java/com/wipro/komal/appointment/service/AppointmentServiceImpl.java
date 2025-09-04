package com.wipro.komal.appointment.service;
import com.wipro.komal.appointment.clients.DoctorClient;
import com.wipro.komal.appointment.clients.PatientClient;
import com.wipro.komal.appointment.dto.DoctorDTO;
import com.wipro.komal.appointment.dto.PatientDTO;
import com.wipro.komal.appointment.entity.Appointment;
import com.wipro.komal.appointment.exception.AppointmentNotFoundException;
import com.wipro.komal.appointment.repo.AppointmentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

  private final AppointmentRepository repo;
  private final DoctorClient doctorClient;
  private final PatientClient patientClient;

  @Override
  public Appointment create(Appointment appt){
    try {
      doctorClient.getDoctor(appt.getDoctorId());
    } catch (Exception e) {
      throw new IllegalArgumentException("Doctor not found or doctor-service unavailable: " + e.getMessage());
    }
    try {
      patientClient.getPatient(appt.getPatientId());
    } catch (Exception e) {
      throw new IllegalArgumentException("Patient not found or patient-service unavailable: " + e.getMessage());
    }
    return repo.save(appt);
  }

  @Override public Page<Appointment> list(int page,int size){ return repo.findAll(PageRequest.of(page,size)); }
  @Override public Appointment get(Long id){ return repo.findById(id).orElseThrow(()->new AppointmentNotFoundException("Appointment "+id+" not found")); }
  @Override public Appointment update(Long id, Appointment appt){ Appointment cur=get(id); appt.setId(cur.getId()); return repo.save(appt); }
  @Override public void delete(Long id){ repo.delete(get(id)); }
  @Override
  @CircuitBreaker(name="doctorClient", fallbackMethod="doctorFallback")
  public Map<String,Object> getDetails(Long id){
    Appointment appt = get(id);
    DoctorDTO doctor = doctorClient.getDoctor(appt.getDoctorId());
    PatientDTO patient = patientClient.getPatient(appt.getPatientId());
    return Map.of("appointment", appt, "doctor", doctor, "patient", patient);
  }
  public Map<String,Object> doctorFallback(Long id, Throwable t){
    Appointment appt = get(id);
    PatientDTO patient = null;
    try { patient = patientClient.getPatient(appt.getPatientId()); } catch(Exception e){ }
    return Map.of("appointment", appt, "doctor", Map.of("id", appt.getDoctorId(), "message", "doctor-service unavailable", "cause", t.getMessage()), "patient", patient);
  }

  @Override public List<Appointment> byDoctor(Long doctorId){ return repo.findByDoctorId(doctorId); }
  @Override public List<Appointment> byPatient(Long patientId){ return repo.findByPatientId(patientId); }
}
