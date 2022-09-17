import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { Role } from '../../acl/Models/role';
import { ResponseMessage } from '../../agri/models/ResponseMessage';
import { PageSyncConfiguration } from '../models/PageSyncConfiguration';
import { SyncConfiguration } from '../models/SyncConfiguration';

@Injectable({
  providedIn: 'root'
})
export class SyncConfigurationService {
// Base url
baseUrl = environment.apiUrl + '/master-data-sync-config';
maxSize: number = 10;

constructor(private http: HttpClient) { }

// Http Headers
httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
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

getSyncConfigurationPagenatedList(page: number, rowSize: number, searchText): Observable<PageSyncConfiguration> {
  this.maxSize = rowSize || this.maxSize;
  var url = this.baseUrl + "/paginatedList" +"?page=" + page + "&size=" + this.maxSize  + "&searchText=" + searchText;
  return this.http.get<PageSyncConfiguration>(url)
      .pipe(
          map(response => {
              const data = response;
              //console.log(data.content);
              return data;
          }));
}


// Reject
rejectSyncConfiguration(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/reject', this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

//  Finalize
activateSyncConfiguration(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/activate', this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}




// POST
  addSyncConfig(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  //PUT
updateSyncConfig(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrl + '/update' + '/'+ id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}


// GET By ID
getSyncConfigById(id): Observable<any> {
  return this.http.get<any>(this.baseUrl + '/' + id)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

 



}