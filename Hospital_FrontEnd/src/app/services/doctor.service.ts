
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Doctor, AvailabilityStatus } from '../models/doctor.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {
//  private apiUrl = `${environment.apiUrl}/api/v1/doctors`;
 private apiUrl="http://localhost:8082/api/v1/doctors"

  constructor(private http: HttpClient) {}

  addDoctor(doctor: Doctor): Observable<Doctor> {
    return this.http.post<Doctor>(`${this.apiUrl}/add`, doctor);
  }

  list(page: number = 0, size: number = 5): Observable<any> {
    const params = new HttpParams().set('page', page.toString()).set('size', size.toString());
    return this.http.get<any>(`${this.apiUrl}/list`, { params });
  }

  get(id: number): Observable<Doctor> {
    return this.http.get<Doctor>(`${this.apiUrl}/${id}`);
  }

  findByAvailability(status: AvailabilityStatus): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.apiUrl}/availability/${status}`);
  }

  update(id: number, doctor: Doctor): Observable<Doctor> {
    return this.http.put<Doctor>(`${this.apiUrl}/update/${id}`, doctor);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }
}
