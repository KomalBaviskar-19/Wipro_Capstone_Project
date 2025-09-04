export interface MedicalRecord {
  id?: number;
  patientId: number;
  doctorId: number;
  appointmentId: number;
  diagnosis?: string;
  prescription?: string;
  recordDate?: string; // ISO string format
}
