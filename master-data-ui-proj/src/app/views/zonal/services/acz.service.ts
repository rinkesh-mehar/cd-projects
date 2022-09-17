import { Acz } from './../models/Acz';
import { ZonalCommodity } from './../models/ZonalCommodity';
import { retry, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AczService {
  // Base url
  baseUrl = environment.apiUrl + '/zonal/acz';
  maxSize: number = 10;

  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  
// Error handling
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

  // GET
  getAllAczByStateCode(stateCode): Observable<Acz> {
    return this.http.get<Acz>(this.baseUrl + '/acz-list-by-stateCode/'+ stateCode)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );
  }
  getStateCodeByAczId(aczId): Observable<Acz> {
    return this.http.get<Acz>(this.baseUrl + '/getStateCodeByAczId/'+ aczId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
}
