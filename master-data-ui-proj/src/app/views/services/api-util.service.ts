import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { ResponseContentType, RequestOptions } from '@angular/http';
import { ResponseMessage } from '../agri/models/ResponseMessage';
import { retry, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiUtilService {

  baseUrl: string = environment.apiUrl + '/api/util';

  constructor(private http: HttpClient) { }


  uploadExcelFile(file) {
    let formData: FormData = new FormData;
    if (file) {
      formData.append("excelFile", file);
    }

    return this.http.post<ResponseMessage>(this.baseUrl + '/upload-excel-file', formData)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  downloadExcelFormat(tableName: string) {
    window.location.href = this.baseUrl + '/download-excel-format?tableName=' + tableName;
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
}
