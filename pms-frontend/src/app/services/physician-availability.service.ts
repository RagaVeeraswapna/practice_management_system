import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Physician } from '../model_classes/physician';

@Injectable({
  providedIn: 'root'
})
export class PhysicianAvailabilityService {
  physicianAvailability: string;
  putUrl: string;
  deleteUrl: string
  availablePhysician: string;
  updateStatus: string;
  count: string;
  findByEmail: string;
  constructor(private http: HttpClient) {
    this.physicianAvailability = 'https://wo9io6hl7d.execute-api.us-west-2.amazonaws.com/physician-prod/physician-availability/physician';
    this.putUrl = 'https://wo9io6hl7d.execute-api.us-west-2.amazonaws.com/physician-prod/physician-availability/update-availability';
    this.deleteUrl = 'https://wo9io6hl7d.execute-api.us-west-2.amazonaws.com/physician-prod/physician-availability/physician-available/';
    this.availablePhysician = 'https://wo9io6hl7d.execute-api.us-west-2.amazonaws.com/physician-prod/physician-availability/physician-available/'
    this.updateStatus = "https://wo9io6hl7d.execute-api.us-west-2.amazonaws.com/physician-prod/physician-availability/update-status";
    this.count = "https://wo9io6hl7d.execute-api.us-west-2.amazonaws.com/physician-prod/physician-availability/count";
    this.findByEmail = "https://wo9io6hl7d.execute-api.us-west-2.amazonaws.com/physician-prod/physician-availability/physician-details"
  }

  public findPhysician(page: number, size: number): Observable<Physician[]> {
    return this.http.get<Physician[]>(this.availablePhysician + page + "/" + size + "/true");
  }
  public findPhysicianByEmailId(email: string): Observable<Physician> {
    return this.http.get<Physician>(this.findByEmail + "/" + email);
  }
  //It is used to fetch all the physicians by the admin only
  public findAllAvailability(): Observable<Physician[]> {
    return this.http.get<Physician[]>(this.physicianAvailability);
  }
  //It is used to update the scheduling of the physician by the admin only
  public updateStartAndEndDate(available: Physician): Observable<Physician> {
    return this.http.put<Physician>(this.updateStatus, available)
  }

  //It is used to delete the physician by the admin only
  public deletePhysicianAvailabilityById(email: any) {
    return this.http.delete(this.deleteUrl + email);
  }

  //It is used to update today's availability
  public update(availability: Physician) {
    return this.http.put(this.updateStatus, availability);
  }

  //It is used to get the count of the physician
  public getCount() {
    return this.http.get(this.count);
  }
}
