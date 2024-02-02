import { Component } from '@angular/core';
import { AlertService } from '../alert.service';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
})
export class AlertComponent {
  message: string | undefined;
  type: 'success' | 'danger' | undefined; // Update type to match the service

  constructor(private alertService: AlertService) {
    this.alertService.getMessage().subscribe((alertMessage) => {
      if (alertMessage) { // Ensure alertMessage is not undefined
        this.message = alertMessage.message;
        this.type = alertMessage.type;
      } else {
        // Handle the case where alertMessage is undefined, e.g., to clear the message
        this.message = undefined;
        this.type = undefined;
      }
    });
  }

  closeAlert() {
    this.message = undefined;
    this.type = undefined; // Also clear the type when closing the alert
  }
}
