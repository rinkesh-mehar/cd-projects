import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import 'rxjs/Rx';
import { ResponseMessage } from './technical-form.model';
import { environment } from '../../../../../../environments/environment';


@Injectable()
export class DrKrishiTechnicalFormService {

  constructor(private http: HttpClient) {
  }

  // Dr Krishi Technical form
  public getDrKrishiTechnicalFormService(taskId): Observable<ResponseMessage> {
    return this.http.get<any>(environment.cctcURL + 'fieldMonitoringDetail/' + taskId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  // NDVI images API
  public getNDVIimages(caseId, currentYear, currentWeek, cropDataApiKey): Observable<any> {
    return this.http.get<any>(environment.GSTMURL+'simple-ndvi/'+'?caseId='+caseId+'&year='+currentYear+'&week='+currentWeek+'&apiKey='+cropDataApiKey+'')
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }
  // Method to save CCTC Tech
  public submitDrKrishiTechnicalForm(data): Observable<ResponseMessage> {
    return this.http.post<any>(environment.cctcURL + 'fieldMonitoringDetail', data)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

}


