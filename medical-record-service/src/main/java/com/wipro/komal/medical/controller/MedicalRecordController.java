package com.wipro.komal.medical.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.wipro.komal.medical.entity.MedicalRecord;
import com.wipro.komal.medical.service.MedicalRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medical-records")
@CrossOrigin("*")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService service;

    @PostMapping("/create")
    public MedicalRecord createRecord(@Valid @RequestBody MedicalRecord record) {
        return service.createMedicalRecord(record);
    }

    @GetMapping("/list")
    public List<MedicalRecord> getAllRecords() {
        return service.getAllRecords();
    }

    @GetMapping("/{id}")
    public MedicalRecord getRecordById(@PathVariable Long id) {
        return service.getRecordById(id);
    }

    @PutMapping("/update/{id}")
    public MedicalRecord updateRecord(@PathVariable Long id, @Valid @RequestBody MedicalRecord record) {
        return service.updateRecord(id, record);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Long id) {
        service.deleteRecord(id);
        return "Medical record with ID " + id + " deleted successfully";
    }
}
