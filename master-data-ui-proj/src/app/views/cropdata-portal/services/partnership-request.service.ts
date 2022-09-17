import { Injectable } from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, map, retry} from 'rxjs/operators';
import { PagePartnershipRequest } from '../models/page-partnership-request';
import { PartnershipRequest } from '../models/partnership-request';

@Injectable({
  providedIn: 'root'
})
export class PartnershipRequestService {

  baseUrls = environment.apiUrl + '/site/partnership-request';

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
getPartnershipRequestList(page: number, rowSize: number, searchText): Observable<PagePartnershipRequest> {
  console.log('Inside getPartnershipRequestList of service..');
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PagePartnershipRequest>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getPartnershipRequestById(id): Observable<PartnershipRequest> {
  return this.http.get<PartnershipRequest>(this.baseUrls + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

}
