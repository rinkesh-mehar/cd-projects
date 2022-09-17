import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { GeneralPackagingType } from '../models/GeneralPackagingType';
import { catchError, map, retry } from 'rxjs/operators';
import { PageGeneralPackagingType } from '../models/PageGeneralPackagingType';
import { ResponseMessage } from '../../agri/models/ResponseMessage';

@Injectable({
  providedIn: 'root'
})
export class GeneralPackagingTypeService {

// Base url
baseUrl = environment.apiUrl + '/general/packaging-type';
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

 // GET
 getAllPackagingType(): Observable<GeneralPackagingType> {
  return this.http.get<GeneralPackagingType>(this.baseUrl + '/list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    );
}


//GET
getPackagingTypePagenatedList(page: number, rowSize: number, searchText): Observable<PageGeneralPackagingType> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageGeneralPackagingType>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

 // Approve
 approvePackagingType(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/primary-approve/' + id , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

 //  Finalize
 finalizePackagingType(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/final-approve/'  + id , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

  // Reject
  rejectPackagingType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + '/reject/'  + id , this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
  getPackagingTypeById(id): Observable<PageGeneralPackagingType> {
  return this.http.get<PageGeneralPackagingType>(this.baseUrl + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

// POST
addPackagingType(data): Observable<ResponseMessage> {

  // console.log("Inside add opp service");

  return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//PUT
updatePackagingType(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrl + '/update/' + id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

}
