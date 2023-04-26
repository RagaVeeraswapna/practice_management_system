import { EventEmitter, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Allergy } from '../model_classes/allergy';

@Injectable({
  providedIn: 'root'
})
export class AllergyService {
  allergy: string;
  appointmentEvent: any;
  constructor(public http: HttpClient) {
    this.allergy = "https://vxyli130nd.execute-api.us-west-2.amazonaws.com/allergy-prod/allergy-service/allergy ";
  }
  public getAllergies(): Observable<Allergy[]> {
    return this.http.get<Allergy[]>(this.allergy);
  }

  public getAllergyById(id: any): Observable<Allergy> {
    return this.http.get<Allergy>(this.allergy + "/" + { id });
  }
  public getAppointmentEvent() {
    return this.appointmentEvent;
  }
  public setAppointmentEvent(event: any) {
    this.appointmentEvent = event;
  }
}
