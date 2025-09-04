

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Appointment, AppointmentDetails } from '../models/appointment.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  //private apiUrl = `${environment.apiUrl}/appointments`;
   private apiUrl="http://localhost:8083/api/v1/appointments"

  constructor(private http: HttpClient) { }

  create(appointment: Appointment): Observable<Appointment> {
    return this.http.post<Appointment>(`${this.apiUrl}/add`, appointment);
  }

  list(page: number = 0, size: number = 5): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<any>(`${this.apiUrl}/list`, { params });
  }

  get(id: number): Observable<Appointment> {
    return this.http.get<Appointment>(`${this.apiUrl}/${id}`);
  }

  update(id: number, appointment: Appointment): Observable<Appointment> {
    return this.http.put<Appointment>(`${this.apiUrl}/update/${id}`, appointment);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }

  getDetails(id: number): Observable<AppointmentDetails> {
    return this.http.get<AppointmentDetails>(`${this.apiUrl}/${id}/details`);
  }

  getByDoctor(doctorId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.apiUrl}/doctor/${doctorId}/list`);
  }

  getByPatient(patientId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.apiUrl}/patient/${patientId}/list`);
  }
}
