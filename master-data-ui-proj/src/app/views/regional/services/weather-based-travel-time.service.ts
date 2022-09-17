import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { WeatherBasedTravelTime } from '../models/WeatherBasedTravelTime';
import { PageWeatherBasedTravelTime } from '../models/PageWeatherBasedTravelTime';

@Injectable({
  providedIn: 'root'
})
export class WeatherBasedTravelTimeService {

   // Base url
   baseUrl = environment.apiUrl+'/weather-based-travel-time';
   maxSize: number = 10;
    
   constructor(private http: HttpClient) { }

// Http Headers
httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}

// GET
getAllTravelTime(): Observable<WeatherBasedTravelTime> {
  return this.http.get<WeatherBasedTravelTime>(this.baseUrl+'/list')
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

// GET By ID
getTravelTime(id): Observable<WeatherBasedTravelTime> {
  return this.http.get<WeatherBasedTravelTime>(this.baseUrl + '/' + id)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

 // POST
 createTravelTime(data): Observable<ResponseMessage> {
  return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
updateTravelTime(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

getPageWeatherBasedTravelTime(page: number, rowSize: number,searchText): Observable<PageWeatherBasedTravelTime> {
  this.maxSize = rowSize || this.maxSize;
  var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + "&searchText=" + searchText;
  return this.http.get<PageWeatherBasedTravelTime>(url)
    .pipe(
      map(response => {
        const data = response;
        return data;
  }));
}

 // Error handling
 errorHandl(error) {
  let errorMessage = '';
  if(error.error instanceof ErrorEvent) {
    // Get client-side error
    errorMessage = error.error.message;
  } else {
    // Get server-side error
    errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
  }
  return throwError(errorMessage);
}

}
