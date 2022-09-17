import { catchError, map, retry } from 'rxjs/operators';
import { ResponseMessage } from './../../farmer/models/ResponseMessage';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { Deployments } from '../models/deployments';
import { PageDeployments } from '../models/page-deployments';

@Injectable({
  providedIn: 'root'
})
export class DeploymentsService {

  // baseUrls = environment.apiUrl + '/deployments';
  baseUrls = environment.azureDevopsUrl + '/deployments';

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


// POST
addDeployment(data): Observable<ResponseMessage> {

  // console.log("Inside add opp service");

  return this.http.post<ResponseMessage>(this.baseUrls + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//GET
getDeploymentById(id): Observable<Deployments> {
  return this.http.get<Deployments>(this.baseUrls + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}


//PUT
updateDeployment(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/update/' + id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

//GET
getDelpoymentListByPagenation(page: number, rowSize: number, searchText): Observable<PageDeployments> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageDeployments>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
deploy(id): Observable<ResponseMessage> {
  return this.http.get<ResponseMessage>(this.baseUrls + '/deploy/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

}
