import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PatientService } from '../../services/patient.service';
import { Patient } from '../../models/patient.model';

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent implements OnInit {
  patientForm: FormGroup;
  patients: Patient[] = [];
  isEditing = false;
  selectedPatientId: number | null = null;
  searchId: string = '';
  loading = false;
  showPassword = false;
  showConfirmPassword = false;

  genders = ['MALE', 'FEMALE', 'OTHER'];

  constructor(
    private fb: FormBuilder,
    private patientService: PatientService
  ) {
    this.patientForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      dateOfBirth: ['', Validators.required],
      gender: ['', Validators.required],
      
    } );
  }

  ngOnInit(): void {
    this.loadPatients();
  }

 

  loadPatients(): void {
    this.loading = true;
    this.patientService.list(0, 10).subscribe(
      (data) => {
        console.log(data);
        this.patients = data.content || [];
        this.loading = false;
      },
      (error) => {
        console.error('Error loading patients:', error);
        this.loading = false;
      }
    );
  }

  searchPatient(): void {
    if (this.searchId.trim()) {
      const id = parseInt(this.searchId);
      this.patientService.get(id).subscribe(
        (patient) => {
          this.populateForm(patient);
          this.isEditing = true;
          this.selectedPatientId = patient.id || null;
        },
        (error) => {
          console.error('Error searching patient:', error);
          alert('Patient not found');
        }
      );
    }
  }

  populateForm(patient: Patient): void {
    this.patientForm.patchValue({
      firstName: patient.firstName,
      lastName: patient.lastName,
      email: patient.email,
      phone: patient.phone,
    
      dateOfBirth: patient.dateOfBirth,
      gender: patient.gender,
     
    });
  }

  onSubmit(): void {
    if (this.patientForm.valid) {
      const patientData = this.patientForm.value;
      
      if (this.isEditing && this.selectedPatientId) {
        this.patientService.update(this.selectedPatientId, patientData).subscribe(
          (response) => {
            alert('Patient updated successfully');
            this.resetForm();
            this.loadPatients();
          },
          (error) => {
            console.error('Error updating patient:', error);
            alert('Error updating patient');
          }
        );
      } else {
        this.patientService.addPatient(patientData).subscribe(
          (response) => {
            alert('Patient added successfully');
            this.resetForm();
            this.loadPatients();
          },
          (error) => {
            console.error('Error adding patient:', error);
            alert('Error adding patient');
          }
        );
      }
    } else {
      this.markFormGroupTouched();
    }
  }

  editPatient(patient: Patient): void {
    this.populateForm(patient);
    this.isEditing = true;
    this.selectedPatientId = patient.id || null;
  }

  deletePatient(id: number): void {
    if (confirm('Are you sure you want to delete this patient?')) {
      this.patientService.delete(id).subscribe(
        (response) => {
          alert('Patient deleted successfully');
          this.loadPatients();
        },
        (error) => {
          console.error('Error deleting patient:', error);
          alert('Error deleting patient');
        }
      );
    }
  }

  resetForm(): void {
    this.patientForm.reset();
    this.isEditing = false;
    this.selectedPatientId = null;
    this.searchId = '';
    this.showPassword = false;
    this.showConfirmPassword = false;
  }

  generateReport(): void {
    alert('Report generation feature will be implemented');
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  toggleConfirmPasswordVisibility(): void {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

  markFormGroupTouched(): void {
    Object.keys(this.patientForm.controls).forEach(key => {
      const control = this.patientForm.get(key);
      control?.markAsTouched();
    });
  }

  getErrorMessage(controlName: string): string {
    const control = this.patientForm.get(controlName);
    if (control?.errors) {
      if (control.errors['required']) {
        return `${controlName} is required`;
      }
      if (control.errors['email']) {
        return 'Please enter a valid email address';
      }
      if (control.errors['minlength']) {
        return `${controlName} must be at least ${control.errors['minlength'].requiredLength} characters`;
      }
      if (control.errors['pattern']) {
        if (controlName === 'mobileNumber') {
          return 'Please enter a valid 10-digit mobile number';
        }
        if (controlName === 'nic') {
          return 'Please enter a valid NIC number (9 digits followed by V or X)';
        }
      }
      if (control.errors['passwordMismatch']) {
        return 'Passwords do not match';
      }
    }
    return '';
  }
} 