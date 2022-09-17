import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { PageReportDownloadRequest } from '../models/page-report-download-request';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ReportDownloadRequestService {

  baseUrls = environment.apiUrl + '/site/report-download-request';

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
getReportDownloadRequestList(page: number, rowSize: number, searchText): Observable<PageReportDownloadRequest> {
  // console.log('Inside getContactRequestList of service..');
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageReportDownloadRequest>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
listOfPageNoOfReportDownloadRequest(): Observable<any> {
  return this.http.get<any>(this.baseUrls + '/total-page-no')
      .pipe(
          catchError(this.errorHandl)
      );
}

exportToExcelReportDownloadRequest(page: number): Observable<any>{
  // window.location.href = this.baseUrls + '/download'  + '?page=' + page;
  return this.http.get<any>(this.baseUrls + '/download'  + '?page=' + page,{responseType: 'blob' as 'json'})
  .pipe(
    catchError(this.errorHandl)
    );
}

}
