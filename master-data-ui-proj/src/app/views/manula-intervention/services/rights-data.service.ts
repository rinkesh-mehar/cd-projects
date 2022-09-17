import { environment } from '../../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { retry, catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { PageRightsData } from '../models/PageRightsData';

@Injectable({
  providedIn: 'root'
})
export class RightsDataService {
  // Base url
  baseUrl = environment.apiUrl + '/manual-intervention/right-data';
  maxSize: number = 10;


  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

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
getPaginatedRighDataList(page: number, rowSize: number, searchText): Observable<PageRightsData> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageRightsData>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}
 
}
