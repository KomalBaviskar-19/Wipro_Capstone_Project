import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  @Input() collapsed = false;
  
  menuItems = [
    { path: '/dashboard', icon: 'dashboard', label: 'Dashboard' },
    { path: '/doctors', icon: 'people', label: 'Staff' },
    { path: '/medical-records', icon: 'science', label: 'Lab' },
    { path: '/billing', icon: 'home', label: 'Ward' },
    { path: '/appointments', icon: 'edit', label: 'Treatment' },
    { path: '/medicine', icon: 'local_pharmacy', label: 'Pharmacy' },
    { path: '/patients', icon: 'person', label: 'Patient' }
  ];

  toggleSidebar() {
    this.collapsed = !this.collapsed;
  }
} 