import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BuyerSupportService {

  baseUrl = environment.warehouseApiUrl + '/public';

  constructor(private http: HttpClient) { }



  // GTE
  getSupportList(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/support-list')
      .pipe(
        

      )
  }

  getSupportDetails(supportId: string) {
    return this.http.get<any>(this.baseUrl + '/support-details/' + supportId)
      .pipe(
        

      )
  }

  sendResponse(supportId, formdata: any) {

    console.log(formdata)
    return this.http.post<any>(this.baseUrl + '/support-response/' + supportId, formdata)
      .pipe(
        

      )
  }
}
