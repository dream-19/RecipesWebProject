import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

// Define an interface for the alert messages
interface AlertMessage {
  message: string;
  type: 'success' | 'danger';
}

@Injectable({ providedIn: 'root' })
export class AlertService {
  // Modify the BehaviorSubject to handle an AlertMessage object
  private messageSubject: BehaviorSubject<AlertMessage | undefined> = new BehaviorSubject<AlertMessage | undefined>(undefined);

  // Update the return type of the getter to match the AlertMessage object
  getMessage(): Observable<AlertMessage | undefined> {
    return this.messageSubject.asObservable();
  }


  // Update setMessage to include the type 'success'
  setMessage(message: string) {
    this.messageSubject.next({ message, type: 'success' });
  }

  // Update setMessageError to include the type 'danger'
  setMessageError(message: string) {
    this.messageSubject.next({ message, type: 'danger' });
  }
}
