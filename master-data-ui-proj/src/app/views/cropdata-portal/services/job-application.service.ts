import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map, retry} from 'rxjs/operators';
import {ResponseMessage} from '../../agri/models/ResponseMessage';
import { PageJobApplication } from '../models/PageJobApplication';
import { JobApplication } from '../models/JobApplication';

@Injectable({
  providedIn: 'root'
})
export class JobApplicationService {

  baseUrls = environment.apiUrl + '/site/job-application';

  maxSize: number = 10;
  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
};

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
getPageJobApplicationList(page: number, rowSize: number, searchText): Observable<PageJobApplication> {
  console.log('Inside getPageJobApplication of service..');
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageJobApplication>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getJobApplicationById(id): Observable<JobApplication> {
  return this.http.get<JobApplication>(this.baseUrls + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

// PUT
shortlistApplication(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/shortlist/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
holdApplication(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/hold/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
scheduleInterview(id,interviewScheduleDate): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/schedule-interview/' + interviewScheduleDate + '/' + id , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
interviewSelection(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/interview-selection/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
rejectApplication(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/reject/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

}
