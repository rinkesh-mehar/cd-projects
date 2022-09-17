import { PageVas } from './../models/page-vas';
import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { Vas } from '../models/vas';
import { ResponseMessage } from '../../agri/models/ResponseMessage';

@Injectable({
  providedIn: 'root'
})
export class VasService {

  baseUrls = environment.apiUrl + '/site/vas';

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
getVasList(): Observable<any> {
  return this.http.get(this.baseUrls + '/list')
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

//GET
getPageVasList(page: number, rowSize: number, searchText): Observable<PageVas> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageVas>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getVasById(id): Observable<Vas> {
  return this.http.get<Vas>(this.baseUrls + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

// POST
addVas(data: any, uploadedFile: any): Observable<ResponseMessage> {

  const formData: FormData = new FormData;
        console.log("uploadedFile : " + uploadedFile);
       if (uploadedFile != undefined){

           formData.append('logoFile', uploadedFile);
       }
        formData.append('data', JSON.stringify(data));

  return this.http.post<ResponseMessage>(this.baseUrls + '/add', formData)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//PUT
updateVas(id, data: any, uploadedLogo: any): Observable<ResponseMessage> {

  const formData: FormData = new FormData;
        if (uploadedLogo != undefined){

            formData.append('logoFile', uploadedLogo);
        }
        formData.append('data', JSON.stringify(data));

  return this.http.put<ResponseMessage>(this.baseUrls + '/update/' + id, formData)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

// PUT
deactiveVas(id) {
  return this.http.put<ResponseMessage>(this.baseUrls + '/deactive' + '/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
activeVas(id) {
  return this.http.put<ResponseMessage>(this.baseUrls + '/active/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
deleteVas(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/delete/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

}
