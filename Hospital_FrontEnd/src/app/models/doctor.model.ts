export type AvailabilityStatus = 'AVAILABLE'| 'UNAVAILABLE' ;

export interface Doctor {
  id?: number;
  name?: string;
  specialization?: string;
  experience?: number;
  contactNumber?: string;
  availabilityStatus?: AvailabilityStatus;
}
