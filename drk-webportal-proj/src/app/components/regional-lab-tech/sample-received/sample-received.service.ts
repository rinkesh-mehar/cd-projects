import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import 'rxjs/Rx';
import { HttpClient } from '@angular/common/http';
import { RltSampleReceivedModel, RtlResponse } from './sample-received.model';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RltSampleReceivedService {

  constructor(private http: HttpClient) { }


  // Method to get RLT Sample Received List
  public getRltSampleReceivedList(id:number): Observable<RltSampleReceivedModel[]> {
    return this.http.get<RltSampleReceivedModel[]>(environment.rltURL+'getSampleList/'+id)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  public getRltSampleData(barcode:string,id:number):Observable<RtlResponse> {
    return this.http.get<any>(environment.rltURL+"getBarcodeDetails/"+barcode+"/"+id)
    .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  public saveRltSampleData(data):Observable<any> {
    return this.http.post<any>(environment.rltURL+"saveBarcodeDetails", data)
    .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  public reassignTask(id:number,userId): Observable<any> {
  
    return this.http.get<any>(environment.rltURL+'needMoreSample/'+id+'/'+userId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

}
