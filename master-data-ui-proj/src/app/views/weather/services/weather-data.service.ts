import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../geo/models/ResponseMessage';
import { WeatherData } from '../models/WeatherData';



@Injectable({
  providedIn: 'root'
})
export class WeatherDataService {

   // Base url
   baseUrl = environment.weatherApiUrl + '/weather';


   constructor(private http: HttpClient) { }
 
   // Http Headers
   httpOptions = {
     headers: new HttpHeaders({
       'Content-Type': 'application/json'
     })
   }
 
  //  // POST
  //  CreateFarmerLeads(data): Observable<ResponseMessage> {
  //    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
  //      .pipe(
  //        retry(1),
  //        catchError(this.errorHandl)
  //      )
  //  }
 
   // GET By ID
   GetWeather(id): Observable<WeatherData> {
     return this.http.get<WeatherData>(this.baseUrl + '/' + id)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
   }
 
   // GET
   GetYaer(): Observable<WeatherData> {
     return this.http.get<WeatherData>(this.baseUrl + '/years')
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
   }

   GetMonthByYear(year): Observable<WeatherData> {
    return this.http.get<WeatherData>(this.baseUrl + '/month' + '/' + year , this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
 
   GetWeekByYearAndMonth(year,month): Observable<WeatherData> {
     return this.http.get<WeatherData>(this.baseUrl + '/weeks' + '/' + year + '/' + month, this.httpOptions)
       .pipe(
         retry(1),
         catchError(this.errorHandl)
       )
   }
 
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
}
