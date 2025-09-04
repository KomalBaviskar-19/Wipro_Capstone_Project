import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css']
})
export class InvoiceComponent implements OnInit {
  invoices: any[] = [];
  loading = false;

  constructor() {
    // Mock data
    this.invoices = [
      { id: 1, patientName: 'John Doe', amount: 5000, status: 'PAID', date: '2024-01-15' },
      { id: 2, patientName: 'Jane Smith', amount: 3500, status: 'PENDING', date: '2024-01-16' },
      { id: 3, patientName: 'Mike Johnson', amount: 7500, status: 'PAID', date: '2024-01-17' }
    ];
  }

  ngOnInit(): void {
  }

  generateInvoice(id: number): void {
    alert(`Generating invoice for ID: ${id}`);
  }

  downloadInvoice(id: number): void {
    alert(`Downloading invoice for ID: ${id}`);
  }

  deleteInvoice(id: number): void {
    if (confirm('Are you sure you want to delete this invoice?')) {
      this.invoices = this.invoices.filter(invoice => invoice.id !== id);
      alert('Invoice deleted successfully');
    }
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'PAID':
        return 'status-online';
      case 'PENDING':
        return 'status-pending';
      default:
        return '';
    }
  }
} 