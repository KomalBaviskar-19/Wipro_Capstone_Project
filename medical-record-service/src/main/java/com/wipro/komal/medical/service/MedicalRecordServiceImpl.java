package com.wipro.komal.medical.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.wipro.komal.medical.clients.AppointmentClient;
import com.wipro.komal.medical.clients.DoctorClient;
import com.wipro.komal.medical.clients.PatientClient;
import com.wipro.komal.medical.dto.PatientDTO;
import com.wipro.komal.medical.dto.DoctorDTO;
import com.wipro.komal.medical.dto.AppointmentDTO;
import com.wipro.komal.medical.entity.MedicalRecord;
import com.wipro.komal.medical.exception.MedicalRecordNotFoundException;
import com.wipro.komal.medical.repository.MedicalRecordRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository repository;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;
    private final AppointmentClient appointmentClient;

    private static final String PATIENT_CB = "patientClient";
    private static final String DOCTOR_CB = "doctorClient";
    private static final String APPOINTMENT_CB = "appointmentClient";

    @CircuitBreaker(name = PATIENT_CB, fallbackMethod = "patientFallback")
    public PatientDTO getPatient(Long patientId) {
        return patientClient.getPatient(patientId);
    }

    @CircuitBreaker(name = DOCTOR_CB, fallbackMethod = "doctorFallback")
    public DoctorDTO getDoctor(Long doctorId) {
        return doctorClient.getDoctor(doctorId);
    }

    @CircuitBreaker(name = APPOINTMENT_CB, fallbackMethod = "appointmentFallback")
    public AppointmentDTO getAppointment(Long appointmentId) {
        return appointmentClient.getAppointment(appointmentId);
    }

    @Override
    public MedicalRecord createMedicalRecord(MedicalRecord record) {
        try {
            getPatient(record.getPatientId());
            getDoctor(record.getDoctorId());
            getAppointment(record.getAppointmentId());
            return repository.save(record);
        } catch (Exception e) {
            throw new MedicalRecordNotFoundException("Failed to create medical record: " + e.getMessage());
        }
    }

    // --- Fallback methods ---
    public PatientDTO patientFallback(Long patientId, Throwable t) {
        throw new MedicalRecordNotFoundException("Patient Service not available: " + t.getMessage());
    }

    public DoctorDTO doctorFallback(Long doctorId, Throwable t) {
        throw new MedicalRecordNotFoundException("Doctor Service not available: " + t.getMessage());
    }

    public AppointmentDTO appointmentFallback(Long appointmentId, Throwable t) {
        throw new MedicalRecordNotFoundException("Appointment Service not available: " + t.getMessage());
    }

    @Override
    public List<MedicalRecord> getAllRecords() {
        return repository.findAll();
    }

    @Override
    public MedicalRecord getRecordById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MedicalRecordNotFoundException("Medical record not found with id: " + id));
    }

    @Override
    public MedicalRecord updateRecord(Long id, MedicalRecord record) {
        MedicalRecord existing = getRecordById(id);
        existing.setDiagnosis(record.getDiagnosis());
     //   existing.setTreatment(record.getTreatment());
        return repository.save(existing);
    }

    @Override
    public void deleteRecord(Long id) {
        MedicalRecord existing = getRecordById(id);
        repository.delete(existing);
    }
}