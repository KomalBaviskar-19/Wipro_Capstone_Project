package com.wipro.komal.billing.controller;

import com.wipro.komal.billing.entity.BillingRecord;
import com.wipro.komal.billing.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/billing")
@CrossOrigin("*")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService service;

    @PostMapping("/create")
    public BillingRecord create(@Valid @RequestBody BillingRecord record) {
        return service.createBilling(record);
    }

    @PostMapping("/pay/{id}")
    public BillingRecord pay(@PathVariable Long id) {
        return service.payBilling(id);
    }

    @GetMapping("/list")
    public List<BillingRecord> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BillingRecord getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/update/{id}")
    public BillingRecord update(@PathVariable Long id, @Valid @RequestBody BillingRecord record) {
        return service.updateBilling(id, record);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteBilling(id);
        return "Billing record deleted successfully for ID " + id;
    }
}
