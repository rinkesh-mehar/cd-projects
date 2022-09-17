import { PageAgriStage } from './../model/PageAgriStage';
import { PageAgriStress } from './../../agri/models/PageAgriStress';
import { environment } from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { ResponseMessage } from '../../agri/models/ResponseMessage';
import { AgriStage } from '../model/AgriStage';

@Injectable({
  providedIn: 'root'
})
export class AgriStageService {

// Base url
baseUrl = environment.apiUrl + '/agri/stage';
maxSize: number = 10;

constructor(private http: HttpClient) {
}

// Http Headers
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

getStageList(): Observable<any> {
  return this.http.get<any>(this.baseUrl + '/list')
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      )
}

//GET
getPageStageList(page: number, rowSize: number, searchText): Observable<PageAgriStage> {
    this.maxSize = rowSize || this.maxSize;
    let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
    return this.http.get<PageAgriStage>(url)
        .pipe(
            map(response => {
                const data = response;
                return data;
            }));
  }
  
   // Approve
   approveStage(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + '/primary-approve/' + id , this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  
   //  Finalize
   finalizeStage(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + '/final-approve/'  + id , this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  
    // Reject
    rejectStage(id) {
      return this.http.put<ResponseMessage>(this.baseUrl + '/reject/'  + id , this.httpOptions)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
    }
  
    //GET
  getStageById(id): Observable<AgriStage> {
    return this.http.get<AgriStage>(this.baseUrl + '/id' + '/' + id)
        .pipe(
            catchError(this.errorHandl)
        );
  }
  
  // POST
  addStage(data): Observable<ResponseMessage> {
  
    // console.log("Inside add opp service");
  
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  
  //PUT
  updateStage(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + '/update/' + id, JSON.stringify(data), this.httpOptions)
        .pipe(
            retry(1),
            catchError(this.errorHandl)
        );
  }

}
