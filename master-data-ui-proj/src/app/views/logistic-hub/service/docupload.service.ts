import { FocusMonitor } from "@angular/cdk/a11y";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable, OnInit } from "@angular/core";
import { Observable, throwError } from "rxjs";
import { catchError, retry } from "rxjs/operators";
import { environment } from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class LogisticHubDocUploadService implements OnInit {

  baseUrl = environment.logisticHubApiUrl + '/document';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  constructor(private httpClient: HttpClient) { }

  ngOnInit() {

  }


  uploadHubDocs(file: File, metaCode, hubId): Observable<any> {
    // console.log(' Service file objects : ' + file);
    let formData: FormData = new FormData();
    if (file) {
      formData.append('file', file);
      formData.append('metaCode', metaCode);
      formData.append('hubId', hubId);
    }
    return this.httpClient
      .post(this.baseUrl + '/uploadKycDocs', formData)
      .pipe(retry(1),
        catchError(this.errorHandl));
  }


  saveHubData(data,hubId): Observable<any> {
    // data.hubId = hubId;
    // let formData: FormData = new FormData();

    return this.httpClient
      .post(this.baseUrl + '/saveKycData?hubId='+hubId, data)
      .pipe(retry(1),
        catchError(this.errorHandl));
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