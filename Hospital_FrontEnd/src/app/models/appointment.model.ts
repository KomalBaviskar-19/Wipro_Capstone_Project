
export interface Appointment {
  id?: number;
  doctorId: number;
  patientId: number;
  scheduledAt: string; // ISO date-time string
  status?: 'SCHEDULED' | 'COMPLETED' | 'CANCELLED';
}

export interface AppointmentDetails {
  appointment: Appointment;
  doctor?: any;
  patient?: any;
  medicalRecords?: any[];
}
