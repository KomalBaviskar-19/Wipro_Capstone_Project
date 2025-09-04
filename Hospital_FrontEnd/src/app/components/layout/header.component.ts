import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  currentUser = 'Prasad';
  currentModule = 'Patient Management';

  toggleSidebar() {
    // This will be handled by the parent component or a service
    // For now, we'll emit an event that can be caught by the parent
    const event = new CustomEvent('toggleSidebar');
    window.dispatchEvent(event);
  }
} 