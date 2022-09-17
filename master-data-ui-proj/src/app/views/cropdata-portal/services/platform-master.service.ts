import { PlatformMaster } from './../models/platform-master';
import { PagePlatformMaster } from './../models/page-platform-master';
import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { ResponseMessage } from '../../agri/models/ResponseMessage';

@Injectable({
  providedIn: 'root'
})
export class PlatformMasterService {

  baseUrls = environment.apiUrl + '/platform';

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
getPlatformList(): Observable<any> {
  return this.http.get(this.baseUrls + '/list')
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

//GET
getPagePlatformList(page: number, rowSize: number, searchText): Observable<PagePlatformMaster> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PagePlatformMaster>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getPlatformById(id): Observable<PlatformMaster> {
  return this.http.get<PlatformMaster>(this.baseUrls + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

// POST
addPlatform(data: any, uploadedFile: any): Observable<ResponseMessage> {

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
updatePlatform(id, data: any, uploadedLogo: any): Observable<ResponseMessage> {

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
deactivePlatform(id) {
  return this.http.put<ResponseMessage>(this.baseUrls + '/deactive' + '/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
activePlatform(id) {
  return this.http.put<ResponseMessage>(this.baseUrls + '/active/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
deletePlatform(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/delete/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

}
