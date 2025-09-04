
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AppointmentService } from '../../services/appointment.service';
import { Appointment } from '../../models/appointment.model';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent implements OnInit {
  appointmentForm: FormGroup;
  appointments: Appointment[] = [];
  isEditing = false;
  selectedAppointmentId: number | null = null;
  loading = false;

  statuses = ['SCHEDULED', 'COMPLETED', 'CANCELLED'];

  constructor(
    private fb: FormBuilder,
    private appointmentService: AppointmentService
  ) {
    this.appointmentForm = this.fb.group({
      doctorId: ['', Validators.required],
      patientId: ['', Validators.required],
      scheduledAt: ['', Validators.required],
      status: ['SCHEDULED', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadAppointments();
  }

  loadAppointments(): void {
    this.loading = true;
    this.appointmentService.list(0, 10).subscribe(
      (data) => {
        this.appointments = data.content || [];
        this.loading = false;
      },
      (error) => {
        console.error('Error loading appointments:', error);
        this.loading = false;
      }
    );
  }

  onSubmit(): void {
    if (this.appointmentForm.valid) {
      const appointmentData: Appointment = this.appointmentForm.value;

      if (this.isEditing && this.selectedAppointmentId) {
        this.appointmentService.update(this.selectedAppointmentId, appointmentData).subscribe(
          () => {
            alert('Appointment updated successfully');
            this.resetForm();
            this.loadAppointments();
          },
          (error) => {
            console.error('Error updating appointment:', error);
            alert('Error updating appointment');
          }
        );
      } else {
        this.appointmentService.create(appointmentData).subscribe(
          () => {
            alert('Appointment created successfully');
            this.resetForm();
            this.loadAppointments();
          },
          (error) => {
            console.error('Error creating appointment:', error);
            alert('Error creating appointment');
          }
        );
      }
    }
  }

  editAppointment(appointment: Appointment): void {
    this.appointmentForm.patchValue({
      doctorId: appointment.doctorId,
      patientId: appointment.patientId,
      scheduledAt: appointment.scheduledAt,
      status: appointment.status || 'SCHEDULED'
    });
    this.isEditing = true;
    this.selectedAppointmentId = appointment.id || null;
  }

  deleteAppointment(id: number): void {
    if (confirm('Are you sure you want to delete this appointment?')) {
      this.appointmentService.delete(id).subscribe(
        () => {
          alert('Appointment deleted successfully');
          this.loadAppointments();
        },
        (error) => {
          console.error('Error deleting appointment:', error);
          alert('Error deleting appointment');
        }
      );
    }
  }

  resetForm(): void {
    this.appointmentForm.reset({ status: 'SCHEDULED' });
    this.isEditing = false;
    this.selectedAppointmentId = null;
  }
}
