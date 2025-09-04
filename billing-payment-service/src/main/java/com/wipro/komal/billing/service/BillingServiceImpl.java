package com.wipro.komal.billing.service;

import com.wipro.komal.billing.clients.AppointmentClient;
import com.wipro.komal.billing.clients.PatientClient;
import com.wipro.komal.billing.entity.BillingRecord;
import com.wipro.komal.billing.entity.enums.BillingStatus;
import com.wipro.komal.billing.exception.BillingNotFoundException;
import com.wipro.komal.billing.repository.BillingRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final BillingRepository repository;
    private final PatientClient patientClient;
    private final AppointmentClient appointmentClient;

    private static final String PATIENT_CB = "patientClient";
    private static final String APPOINTMENT_CB = "appointmentClient";

    @Override
    public BillingRecord createBilling(BillingRecord record) {
        // Validate patient & appointment via separate methods
        callPatientService(record.getPatientId());
        callAppointmentService(record.getAppointmentId());

        record.setStatus(BillingStatus.PENDING);
        record.setIssuedOn(LocalDateTime.now());
        return repository.save(record);
    }

    @CircuitBreaker(name = PATIENT_CB, fallbackMethod = "patientFallback")
    private void callPatientService(Long patientId) {
        patientClient.getPatient(patientId);
    }

    @CircuitBreaker(name = APPOINTMENT_CB, fallbackMethod = "appointmentFallback")
    private void callAppointmentService(Long appointmentId) {
        appointmentClient.getAppointment(appointmentId);
    }


    public BillingRecord patientFallback(BillingRecord record, Throwable t) {
        throw new BillingNotFoundException("Patient Service unavailable: " + t.getMessage());
    }

    public BillingRecord appointmentFallback(BillingRecord record, Throwable t) {
        throw new BillingNotFoundException("Appointment Service unavailable: " + t.getMessage());
    }

    @Override
    public BillingRecord payBilling(Long id) {
        BillingRecord existing = getById(id);
        // Simulate payment success
        existing.setStatus(BillingStatus.PAID);
        return repository.save(existing);
    }

    @Override
    public List<BillingRecord> getAll() {
        return repository.findAll();
    }

    @Override
    public BillingRecord getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BillingNotFoundException("Billing record not found with id: " + id));
    }

    @Override
    public BillingRecord updateBilling(Long id, BillingRecord record) {
        BillingRecord existing = getById(id);
        existing.setAmount(record.getAmount());
        existing.setStatus(record.getStatus());
        return repository.save(existing);
    }

    @Override
    public void deleteBilling(Long id) {
        BillingRecord existing = getById(id);
        repository.delete(existing);
    }
}
