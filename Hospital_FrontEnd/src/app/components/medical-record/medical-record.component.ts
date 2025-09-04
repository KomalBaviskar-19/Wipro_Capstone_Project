import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MedicalRecordService } from '../../services/medical-record.service';
import { MedicalRecord } from '../../models/medical-record.model';

@Component({
  selector: 'app-medical-record',
  templateUrl: './medical-record.component.html',
  styleUrls: ['./medical-record.component.css']
})
export class MedicalRecordComponent implements OnInit {
  medicalRecordForm: FormGroup;
  medicalRecords: MedicalRecord[] = [];
  isEditing = false;
  selectedRecordId: number | null = null;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private medicalRecordService: MedicalRecordService
  ) {
    this.medicalRecordForm = this.fb.group({
      patientId: ['', [Validators.required, Validators.min(1)]],
      doctorId: ['', [Validators.required, Validators.min(1)]],
      appointmentId: ['', [Validators.required, Validators.min(1)]],
      diagnosis: [''],
      prescription: [''],
      recordDate: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadMedicalRecords();
  }

  loadMedicalRecords(): void {
    this.loading = true;
    this.medicalRecordService.getAllRecords().subscribe(
      (data) => {
        this.medicalRecords = data;
        this.loading = false;
      },
      (error) => {
        console.error('Error loading medical records:', error);
        this.loading = false;
      }
    );
  }

  onSubmit(): void {
    if (this.medicalRecordForm.valid) {
      const recordData = this.medicalRecordForm.value;

      if (this.isEditing && this.selectedRecordId) {
        this.medicalRecordService.updateRecord(this.selectedRecordId, recordData).subscribe(
          (response) => {
            alert('Medical record updated successfully');
            this.resetForm();
            this.loadMedicalRecords();
          },
          (error) => {
            console.error('Error updating medical record:', error);
            alert('Error updating medical record');
          }
        );
      } else {
        this.medicalRecordService.createMedicalRecord(recordData).subscribe(
          (response) => {
            alert('Medical record created successfully');
            this.resetForm();
            this.loadMedicalRecords();
          },
          (error) => {
            console.error('Error creating medical record:', error);
            alert('Error creating medical record');
          }
        );
      }
    } else {
      this.markFormGroupTouched();
    }
  }

  editRecord(record: MedicalRecord): void {
    this.medicalRecordForm.patchValue({
      patientId: record.patientId,
      doctorId: record.doctorId,
      appointmentId: record.appointmentId,
      diagnosis: record.diagnosis,
      prescription: record.prescription,
      recordDate: record.recordDate?.split('T')[0]
    });
    this.isEditing = true;
    this.selectedRecordId = record.id || null;
  }

  deleteRecord(id: number): void {
    if (confirm('Are you sure you want to delete this medical record?')) {
      this.medicalRecordService.deleteRecord(id).subscribe(
        () => {
          alert('Medical record deleted successfully');
          this.loadMedicalRecords();
        },
        (error) => {
          console.error('Error deleting medical record:', error);
          alert('Error deleting medical record');
        }
      );
    }
  }

  resetForm(): void {
    this.medicalRecordForm.reset();
    this.isEditing = false;
    this.selectedRecordId = null;
  }

  markFormGroupTouched(): void {
    Object.keys(this.medicalRecordForm.controls).forEach(key => {
      const control = this.medicalRecordForm.get(key);
      control?.markAsTouched();
    });
  }
}
