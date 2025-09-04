package com.wipro.komal.medical.service;

import java.util.List;

import com.wipro.komal.medical.entity.MedicalRecord;

public interface MedicalRecordService {
    MedicalRecord createMedicalRecord(MedicalRecord record);
    List<MedicalRecord> getAllRecords();
    MedicalRecord getRecordById(Long id);
    MedicalRecord updateRecord(Long id, MedicalRecord record);
    void deleteRecord(Long id);
}
