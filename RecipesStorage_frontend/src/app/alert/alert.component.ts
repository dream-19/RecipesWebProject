// alert.component.ts
import { Component } from '@angular/core';
import { AlertService } from '../alert.service';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
})
export class AlertComponent {
  message: string | undefined;
  type: string | undefined;

  //get message and type of alert
  constructor(private alertService: AlertService) {
    this.alertService.getMessage().subscribe((message) => {
      this.message = message;
      this.type = 'success';
    });

    this.alertService.getMessageError().subscribe((message) => {
      this.message = message;
      this.type = 'danger';
    });
  }

  closeAlert() {
    this.message = undefined;
  }
}
