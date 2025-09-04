
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BillingRecord } from '../models/billing.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BillingService {
 // private apiUrl = `${environment.apiUrl}/billing`;
 private apiUrl="http://localhost:8085/api/v1/billing"

  constructor(private http: HttpClient) { }

  createBilling(record: BillingRecord): Observable<BillingRecord> {
    return this.http.post<BillingRecord>(`${this.apiUrl}/create`, record);
  }

  payBilling(id: number): Observable<BillingRecord> {
    return this.http.post<BillingRecord>(`${this.apiUrl}/pay/${id}`, {});
  }

  getAll(): Observable<BillingRecord[]> {
    return this.http.get<BillingRecord[]>(`${this.apiUrl}/list`);
  }

  getById(id: number): Observable<BillingRecord> {
    return this.http.get<BillingRecord>(`${this.apiUrl}/${id}`);
  }

  updateBilling(id: number, record: BillingRecord): Observable<BillingRecord> {
    return this.http.put<BillingRecord>(`${this.apiUrl}/update/${id}`, record);
  }

  deleteBilling(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }
}
