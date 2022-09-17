import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EnquiriesService {


  // /contact-list


  baseUrl = environment.warehouseApiUrl + '/public';

  constructor(private http: HttpClient) { }


  // Get
  getEnquiriesList(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/contact-list')
      .pipe(
        

      )
  }

  getEnquiry(enquiryId: string) {
    return this.http.get<any>(this.baseUrl + '/contact-details/' + enquiryId)
      .pipe(
        

      )
  }

  sendResponse(enquiryId, formdata: any) {

    console.log(formdata)
    return this.http.post<any>(this.baseUrl + '/contact-response/' + enquiryId, formdata)
      .pipe(
        

      )
  }


}
