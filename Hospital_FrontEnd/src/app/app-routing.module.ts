import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Components
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AppointmentComponent } from './components/appointment/appointment.component';
import { PatientComponent } from './components/patient/patient.component';
import { DoctorComponent } from './components/doctor/doctor.component';
import { MedicalRecordComponent } from './components/medical-record/medical-record.component';
import { BillingComponent } from './components/billing/billing.component';
// import { MedicineComponent } from './components/medicine/medicine.component';
import { InvoiceComponent } from './components/invoice/invoice.component';
import { LoginComponent } from './components/auth/login.component';
import { RegisterComponent } from './components/auth/register.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'appointments', component: AppointmentComponent },
  { path: 'patients', component: PatientComponent },
  { path: 'doctors', component: DoctorComponent },
  { path: 'medical-records', component: MedicalRecordComponent },
  { path: 'billing', component: BillingComponent },
  // { path: 'medicine', component: MedicineComponent },
  { path: 'invoice', component: InvoiceComponent },
  { path: '**', redirectTo: '/dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
