import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MedicalRecord } from '../models/medical-record.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MedicalRecordService {
  // private apiUrl = `${environment.apiUrl}/medical-records`;
   private apiUrl="http://localhost:8084/api/v1/medical-records"

  constructor(private http: HttpClient) { }

  createMedicalRecord(record: MedicalRecord): Observable<MedicalRecord> {
    return this.http.post<MedicalRecord>(`${this.apiUrl}/create`, record);
  }

  getAllRecords(): Observable<MedicalRecord[]> {
    return this.http.get<MedicalRecord[]>(`${this.apiUrl}/list`);
  }

  getRecordById(id: number): Observable<MedicalRecord> {
    return this.http.get<MedicalRecord>(`${this.apiUrl}/${id}`);
  }

  updateRecord(id: number, record: MedicalRecord): Observable<MedicalRecord> {
    return this.http.put<MedicalRecord>(`${this.apiUrl}/update/${id}`, record);
  }

  deleteRecord(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }
}
