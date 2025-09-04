

export interface BillingRecord {
  id?: number;
  patientId: number;
  appointmentId: number;
  amount: number;
  status: 'PENDING' | 'PAID' | 'FAILED';
  issuedOn?: string;
}
