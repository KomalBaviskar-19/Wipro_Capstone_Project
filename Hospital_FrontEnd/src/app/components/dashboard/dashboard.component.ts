import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../../services/appointment.service';
import { DoctorService } from '../../services/doctor.service';
import { PatientService } from '../../services/patient.service';
import { BillingService } from '../../services/billing.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  summaryData = {
    totalPatients: 20,
    totalDoctors: 20,
    totalWards: 20,
    totalLabs: 20
  };

  patientActivity = {
    labels: ['Mon 14', 'Tue 15', 'Wed 16', 'Thu 17', 'Fri 18', 'Sat 19', 'Sun 20'],
    data: [1200, 1800, 2212, 1900, 1600, 1400, 1100]
  };

  appointments = [
    { name: 'Chance Vaccaro', date: '10.01.2003 12:54', status: 'Pending' },
    { name: 'Desirae Kenter', date: '04.12.2003 03:21', status: 'Rejected' },
    { name: 'Paityn Lubin', date: '10.01.2003 12:54', status: 'Pending' },
    { name: 'Phillip Bator', date: '04.12.2003 03:21', status: 'Pending' },
    { name: 'Emerson Stanton', date: '10.01.2003 12:54', status: 'Accept' },
    { name: 'Alfredo Rhiel Madsen', date: '03.08.2019 12:54', status: 'Rejected' }
  ];

  recentDoctors = [
    { id: 1, name: 'Sam', mobile: '075553001', address: 'Kalutara', consultancyCharge: 2500.00, education: 'MBBS', dob: '1954-04-13', status: 'Online' },
    { id: 2, name: 'John', mobile: '0724725839', address: 'Kandy', consultancyCharge: 2500.00, education: 'PhD', dob: '1978-05-13', status: 'Offline' },
    { id: 3, name: 'David', mobile: '0771234567', address: 'Galle', consultancyCharge: 2500.00, education: 'MBBS', dob: '1987-04-18', status: 'Offline' },
    { id: 4, name: 'Christiano', mobile: '0769876543', address: 'Matara', consultancyCharge: 2500.00, education: 'MBBS', dob: '1988-06-13', status: 'Offline' }
  ];

  outOfStock = [
    { id: 1, drugName: 'Vitamin C', expireDate: '1925-04-13', manufactureDate: '2021-12-13', price: 1500.00, qty: 150 },
    { id: 2, drugName: 'Paracetamol', expireDate: '2020-05-13', manufactureDate: '2002-04-04', price: 4800.00, qty: 225 },
    { id: 3, drugName: 'Actos', expireDate: '2026-01-16', manufactureDate: '2006-06-09', price: 5000.00, qty: 65 },
    { id: 4, drugName: 'Amoxicillin', expireDate: '2024-12-13', manufactureDate: '2021-01-13', price: 1200.00, qty: 276 }
  ];

  currentPage = 1;
  totalPages = 13;

  constructor(
    private appointmentService: AppointmentService,
    private doctorService: DoctorService,
    private patientService: PatientService,
    private billingService: BillingService
  ) { }

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    // Load summary data
    this.loadSummaryData();
    
    // Load recent appointments
    this.loadRecentAppointments();
    
    // Load recent doctors
    this.loadRecentDoctors();
    
    // Load out of stock items
    this.loadOutOfStock();
  }

  loadSummaryData(): void {
    // In a real application, you would call services to get actual data
    // For now, using mock data
  }

  loadRecentAppointments(): void {
    this.appointmentService.list(0, 6).subscribe(
      (data) => {
        // Handle appointment data
      },
      (error) => {
        console.error('Error loading appointments:', error);
      }
    );
  }

  loadRecentDoctors(): void {
    this.doctorService.list(0, 4).subscribe(
      (data) => {
        // Handle doctor data
      },
      (error) => {
        console.error('Error loading doctors:', error);
      }
    );
  }

  loadOutOfStock(): void {
    // This would typically come from a medicine/inventory service
  }

  getStatusClass(status: string): string {
    switch (status.toLowerCase()) {
      case 'online':
      case 'accept':
        return 'status-online';
      case 'offline':
      case 'rejected':
        return 'status-offline';
      case 'pending':
        return 'status-pending';
      default:
        return '';
    }
  }

  acceptAppointment(appointment: any): void {
    // Handle appointment acceptance
    console.log('Accepting appointment:', appointment);
  }

  rejectAppointment(appointment: any): void {
    // Handle appointment rejection
    console.log('Rejecting appointment:', appointment);
  }

  deleteDoctor(doctorId: number): void {
    // Handle doctor deletion
    console.log('Deleting doctor:', doctorId);
  }

  deleteMedicine(medicineId: number): void {
    // Handle medicine deletion
    console.log('Deleting medicine:', medicineId);
  }
} 