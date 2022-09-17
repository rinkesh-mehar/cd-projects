import { ResponseMessage } from '../../farmer/models/ResponseMessage';
import {environment} from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class FlsVisitsService {

  baseUrls = environment.apiUrl + '/site/holiday-calendar';


  // mapBaseUrls = 'http://192.168.0.83:8099/post/task-view';

  mapBaseUrls = 'http://192.168.0.83:8099/get/task-view?region_id=';


  //baseUrls = environment.apiUrl + '/site/holiday-calendar';

  constructor(private http: HttpClient) { }

  errorHandl(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
        // Get client-side error
        errorMessage = error.error.message;
    } else {
        // Get server-side error
        errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  }


  httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
  };

  // GET
  getAllGeoRegion(): Observable<any> {
    return this.http.get(this.baseUrls + '/region-list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }



  // Get
  getUrl(region_id: number): Observable<any> {
    console.log(this.mapBaseUrls + region_id)
    return this.http.get<any>(this.mapBaseUrls + region_id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // // POST
  // getUrl(data): Observable<any> {
  //   console.log(this.mapBaseUrls)
  //   return this.http.post<any>(this.mapBaseUrls, JSON.stringify(data), this.httpOptions)
  //     .pipe(
  //       retry(1),
  //       catchError(this.errorHandl)
  //     )
  // }
  
}
