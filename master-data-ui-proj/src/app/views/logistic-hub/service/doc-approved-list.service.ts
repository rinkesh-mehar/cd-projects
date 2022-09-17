import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';



@Injectable({
  providedIn: 'root'
})
export class DocApprovedListService {

  baseUrl = environment.logisticHubApiUrl + '/logisticHub';
  maxSize: number = 10;

  constructor(private httpClient: HttpClient) { }
  httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json'})
  };

  getAllDocApprovedLogisticHub(page: number, rowSize: number, searchText):Observable<any>{
    this.maxSize = rowSize || this.maxSize;
    // return this.httpClient.get(this.baseUrl +'/getAllDocApprovedLogisticHub').pipe(retry(1),catchError(this.errorHandl));
    let url = this.baseUrl + '/getAllDocApprovedLogisticHub' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.httpClient.get<any>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
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

}