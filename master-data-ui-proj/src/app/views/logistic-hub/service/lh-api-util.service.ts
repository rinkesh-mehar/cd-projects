import { HttpClient } from '@angular/common/http';
import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LhApiUtilService {

  baseUrl: string = environment.logisticHubApiUrl + '/api/util';

  constructor(private http: HttpClient) { }


  downloadExcelFormat(fileName: string) {
    window.location.href = this.baseUrl + '/download-excel-format?fileName=' + fileName;
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
