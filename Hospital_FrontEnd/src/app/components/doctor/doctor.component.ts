
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Doctor, AvailabilityStatus } from '../../models/doctor.model';
import { DoctorService } from '../../services/doctor.service';

@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.css']
})
export class DoctorComponent implements OnInit {
  doctorForm: FormGroup;
  doctors: Doctor[] = [];
  selectedDoctorId: number | null = null;
  loading = false;
  searchId: string = '';

  availabilityStatusOptions: AvailabilityStatus[] = ['AVAILABLE', 'UNAVAILABLE'];

  constructor(private fb: FormBuilder, private doctorService: DoctorService) {
    this.doctorForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      specialization: ['', Validators.required],
      experience: [0, [Validators.required, Validators.min(0)]],
      contactNumber: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      availabilityStatus: ['Available', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadDoctors();
  }

  loadDoctors(): void {
    this.loading = true;
    this.doctorService.list(0, 10).subscribe(
      data => {
        this.doctors = data.content || [];
        this.loading = false;
      },
      error => {
        console.error(error);
        this.loading = false;
      }
    );
  }

  searchDoctor(): void {
    if (this.searchId.trim()) {
      const id = parseInt(this.searchId);
      this.doctorService.get(id).subscribe(
        doctor => {
          this.populateForm(doctor);
          this.selectedDoctorId = doctor.id || null;
        },
        error => alert('Doctor not found')
      );
    }
  }

  populateForm(doctor: Doctor): void {
    this.doctorForm.patchValue({
      name: doctor.name,
      specialization: doctor.specialization,
      experience: doctor.experience,
      contactNumber: doctor.contactNumber,
      availabilityStatus: doctor.availabilityStatus || 'Available'
    });
  }

  onSubmit(): void {
    if (this.doctorForm.valid) {
      const doctorData = this.doctorForm.value as Doctor;

      if (this.selectedDoctorId) {
        this.doctorService.update(this.selectedDoctorId, doctorData).subscribe(
          () => {
            alert('Doctor updated successfully');
            this.resetForm();
            this.loadDoctors();
          },
          error => console.error(error)
        );
      } else {
        this.doctorService.addDoctor(doctorData).subscribe(
          () => {
            alert('Doctor added successfully');
            this.resetForm();
            this.loadDoctors();
          },
          error => console.error(error)
        );
      }
    } else {
      this.markFormGroupTouched();
    }
  }

  editDoctor(doctor: Doctor): void {
    this.populateForm(doctor);
    this.selectedDoctorId = doctor.id || null;
  }

  deleteDoctor(id: number): void {
    if (confirm('Are you sure you want to delete this doctor?')) {
      this.doctorService.delete(id).subscribe(() => {
        alert('Doctor deleted successfully');
        this.loadDoctors();
      });
    }
  }

  resetForm(): void {
    this.doctorForm.reset({ availabilityStatus: 'Available' });
    this.selectedDoctorId = null;
    this.searchId = '';
  }

  markFormGroupTouched(): void {
    Object.values(this.doctorForm.controls).forEach(control => control.markAsTouched());
  }
}
