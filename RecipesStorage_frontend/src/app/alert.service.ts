// alert.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AlertService {
  //to send message to alert component and type of alert
  private messageSubject: BehaviorSubject<string | undefined> = new BehaviorSubject<string | undefined>(undefined);

  getMessage(): Observable<string | undefined> {
    return this.messageSubject.asObservable();
  }

  getMessageError(): Observable<string | undefined> {
    return this.messageSubject.asObservable();
  }

  setMessage(message: string) {
    this.messageSubject.next(message);
  }

  setMessageError(message: string) {
    this.messageSubject.next(message);
  }
}
