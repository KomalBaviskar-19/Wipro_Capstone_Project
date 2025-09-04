import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'Hospital Management System';
  sidebarCollapsed = false;

  ngOnInit() {
    // Listen for sidebar toggle events from header
    window.addEventListener('toggleSidebar', this.handleSidebarToggle.bind(this));
  }

  ngOnDestroy() {
    // Clean up event listener
    window.removeEventListener('toggleSidebar', this.handleSidebarToggle.bind(this));
  }

  handleSidebarToggle() {
    this.sidebarCollapsed = !this.sidebarCollapsed;
  }

  toggleSidebar() {
    this.sidebarCollapsed = !this.sidebarCollapsed;
  }
}
