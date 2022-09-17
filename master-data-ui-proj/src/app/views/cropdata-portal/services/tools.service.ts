import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { PageTools } from '../models/page-tools';
import { Tools } from '../models/tools';
import { ResponseMessage } from '../../agri/models/ResponseMessage';

@Injectable({
  providedIn: 'root'
})
export class ToolsService {

  baseUrls = environment.apiUrl + '/site/tools';

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
getToolList(): Observable<any> {
  return this.http.get(this.baseUrls + '/list')
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

//GET
getPageToolList(page: number, rowSize: number, searchText): Observable<PageTools> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageTools>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getToolById(id): Observable<Tools> {
  return this.http.get<Tools>(this.baseUrls + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

// POST
addTool(data: any, uploadedFile: any): Observable<ResponseMessage> {

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
updateTool(id, data: any, uploadedLogo: any): Observable<ResponseMessage> {

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
deactiveTool(id) {
  return this.http.put<ResponseMessage>(this.baseUrls + '/deactive' + '/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
activeTool(id) {
  return this.http.put<ResponseMessage>(this.baseUrls + '/active/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
deleteTool(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/delete/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

}
