
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BillingService } from '../../services/billing.service';
import { BillingRecord } from '../../models/billing.model';

@Component({
  selector: 'app-billing',
  templateUrl: './billing.component.html',
  styleUrls: ['./billing.component.css']
})
export class BillingComponent implements OnInit {
  billingForm: FormGroup;
  billingRecords: BillingRecord[] = [];
  isEditing = false;
  selectedBillingId: number | null = null;
  loading = false;

  statusOptions = ['PENDING', 'PAID', 'FAILED'];

  constructor(
    private fb: FormBuilder,
    private billingService: BillingService
  ) {
    this.billingForm = this.fb.group({
      patientId: ['', [Validators.required, Validators.min(1)]],
      appointmentId: ['', [Validators.min(1)]],
      amount: ['', [Validators.required, Validators.min(0)]],
      status: ['PENDING', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadBillingRecords();
  }

  loadBillingRecords(): void {
    this.loading = true;
    this.billingService.getAll().subscribe(
      (data) => {
        this.billingRecords = data;
        this.loading = false;
      },
      (error) => {
        console.error('Error loading billing records:', error);
        this.loading = false;
      }
    );
  }

  onSubmit(): void {
    if (this.billingForm.valid) {
      const billingData = this.billingForm.value;

      if (this.isEditing && this.selectedBillingId) {
        this.billingService.updateBilling(this.selectedBillingId, billingData).subscribe(
          (response) => {
            alert('Billing record updated successfully');
            this.resetForm();
            this.loadBillingRecords();
          },
          (error) => {
            console.error('Error updating billing record:', error);
            alert('Error updating billing record');
          }
        );
      } else {
        this.billingService.createBilling(billingData).subscribe(
          (response) => {
            alert('Billing record created successfully');
            this.resetForm();
            this.loadBillingRecords();
          },
          (error) => {
            console.error('Error creating billing record:', error);
            alert('Error creating billing record');
          }
        );
      }
    } else {
      this.markFormGroupTouched();
    }
  }

  // THIS IS THE PAY METHOD
  payBilling(id: number): void {
    this.billingService.payBilling(id).subscribe(
      (response) => {
        alert('Payment processed successfully');
        this.loadBillingRecords();
      },
      (error) => {
        console.error('Error processing payment:', error);
        alert('Error processing payment');
      }
    );
  }

  editBilling(billing: BillingRecord): void {
    this.billingForm.patchValue({
      patientId: billing.patientId,
      appointmentId: billing.appointmentId || '',
      amount: billing.amount,
      status: billing.status
    });
    this.isEditing = true;
    this.selectedBillingId = billing.id || null;
  }

  deleteBilling(id: number): void {
    if (confirm('Are you sure you want to delete this billing record?')) {
      this.billingService.deleteBilling(id).subscribe(
        (response) => {
          alert('Billing record deleted successfully');
          this.loadBillingRecords();
        },
        (error) => {
          console.error('Error deleting billing record:', error);
          alert('Error deleting billing record');
        }
      );
    }
  }

  resetForm(): void {
    this.billingForm.reset({
      status: 'PENDING'
    });
    this.isEditing = false;
    this.selectedBillingId = null;
  }

  markFormGroupTouched(): void {
    Object.keys(this.billingForm.controls).forEach(key => {
      const control = this.billingForm.get(key);
      control?.markAsTouched();
    });
  }

  getErrorMessage(controlName: string): string {
    const control = this.billingForm.get(controlName);
    if (control?.errors) {
      if (control.errors['required']) return `${controlName} is required`;
      if (control.errors['min']) return `${controlName} must be greater than ${control.errors['min'].min}`;
      if (control.errors['minlength']) return `${controlName} must be at least ${control.errors['minlength'].requiredLength} characters`;
    }
    return '';
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'PAID': return 'status-online';
      case 'PENDING': return 'status-pending';
      case 'FAILED': return 'status-offline';
      default: return '';
    }
  }
}



