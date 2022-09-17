import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { AgriMeteorologicalWeek } from '../models/AgriMeteorologicalWeek';

@Injectable({
  providedIn: 'root'
})
export class AgriMeteorologicalWeekService {

  baseUrl = environment.apiUrl + '/agri/meteorological-week';

  constructor(private http: HttpClient) { }


  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateAgriMeteorologicalWeek(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetAgriMeteorologicalWeek(id): Observable<AgriMeteorologicalWeek> {
    return this.http.get<AgriMeteorologicalWeek>(this.baseUrl + '/' + id)
      .pipe(
        catchError(this.errorHandl)
      )
  }


  // GET
  GetAllAgriMeteorologicalWeek(): Observable<AgriMeteorologicalWeek> {
    return this.http.get<AgriMeteorologicalWeek>(this.baseUrl + '/list')
      .pipe(
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateAgriMeteorologicalWeek(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteAgriMeteorologicalWeek(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectMeteorologicalWeek(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeMeteorologicalWeek(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveMeteorologicalWeek(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
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
