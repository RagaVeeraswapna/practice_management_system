import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppointmentDto } from '../model_classes/appointment';
import { PrescriptionDetails } from '../model_classes/prescription';
import { TestDetails } from '../model_classes/test';
import { PatientInfoDetails } from '../model_classes/visit_details';

@Injectable({
  providedIn: 'root'
})
export class PatientHealthRecordService {

  constructor(private http: HttpClient) { }

  public healthRecords(patientId: number): Observable<PatientInfoDetails[]> {
    return this.http.get<PatientInfoDetails[]>('https://lc5w14ws23.execute-api.us-west-2.amazonaws.com/healthrecord-prod/health-record/patient/' + patientId + '/visits');
  }
  public visitDetails(visitId: number): Observable<PatientInfoDetails> {
    return this.http.get<PatientInfoDetails>('https://lc5w14ws23.execute-api.us-west-2.amazonaws.com/healthrecord-prod/health-record/visitdetails/' + visitId);
  }

  public postPrescription(prescription: any) {
    return this.http.post("https://lc5w14ws23.execute-api.us-west-2.amazonaws.com/healthrecord-prod/health-record/visitdetails/prescription", prescription)
  }
  public postTest(Test: any) {
    console.log("tanu")
    console.log(Test);
    return this.http.post("https://lc5w14ws23.execute-api.us-west-2.amazonaws.com/healthrecord-prod/health-record/visitdetails/tests", Test)
  }
  public getTests(Id: any): Observable<TestDetails[]> {
    return this.http.get<TestDetails[]>("https://lc5w14ws23.execute-api.us-west-2.amazonaws.com/healthrecord-prod/health-record/tests/" + Id);
  }

  public getNurses() {
    return this.http.get("https://lc5w14ws23.execute-api.us-west-2.amazonaws.com/healthrecord-prod/health-record/nurse");
  }
  public getPrescription(id: any): Observable<PrescriptionDetails[]> {
    return this.http.get<PrescriptionDetails[]>("https://lc5w14ws23.execute-api.us-west-2.amazonaws.com/healthrecord-prod/health-record/prescriptions/" + id);
  }

  public getVisitDetails(
    appointmentId: any
  ): Observable<PatientInfoDetails> {
    return this.http.get<PatientInfoDetails>(
      'https://lc5w14ws23.execute-api.us-west-2.amazonaws.com/healthrecord-prod/health-record/visit/' + appointmentId
    );
  }

  public getNurseCount() {
    return this.http.get("https://lc5w14ws23.execute-api.us-west-2.amazonaws.com/healthrecord-prod/health-record/nurse/count");
  }


  // to get test details

  public getTestDetails(visitId: any): Observable<TestDetails[]> {
    return this.http.get<TestDetails[]>(
      'https://lc5w14ws23.execute-api.us-west-2.amazonaws.com/healthrecord-prod/health-record/visitdetails/' + visitId + '/tests'
    );
  }

  // to get Prescription Details
  public getPrescriptionDetails(
    visitId: any
  ): Observable<PrescriptionDetails[]> {
    return this.http.get<PrescriptionDetails[]>(
      'https://lc5w14ws23.execute-api.us-west-2.amazonaws.com/healthrecord-prod/health-record/visitdetails/' + visitId + '/prescription'
    );
  }
}
