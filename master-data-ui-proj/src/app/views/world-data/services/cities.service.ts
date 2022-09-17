import { retry, catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CitiesService {
  // Base url
  baseUrl = environment.apiUrl + '/world-data/cities';
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

//GET
getCitiesList(): Observable<any> {
  return this.http.get(this.baseUrl + '/list')
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

getCitiesListByCountryAndState(countryCode,stateCode): Observable<any> {
  return this.http.get(this.baseUrl + '/getCitiesListByCountryAndState/' + countryCode + "/" + stateCode)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}
  
}
