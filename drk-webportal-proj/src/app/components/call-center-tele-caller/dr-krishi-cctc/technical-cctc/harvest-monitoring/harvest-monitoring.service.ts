import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';

import { ResponseMessage } from '../technical-form/technical-form.model';
import { Injectable } from '@angular/core';
import { saveSchedule } from '../technical-form/technical-form.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HarvestMonitoringService {

  constructor(private http: HttpClient) { }

  public getHarvestMonitoringFormService(taskId): Observable<ResponseMessage> {
    return this.http.get<any>(environment.cctcURL + 'harvestMonitoring/' + taskId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  public submitHarvestMonitoringForm(data): Observable<ResponseMessage> {
    return this.http.post<any>(environment.cctcURL + 'fieldMonitoringDetail', data)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }
}
