import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { HttpEvent, HttpClient, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import 'rxjs/Rx';
import { approveCorrection } from './approve-with-correction.model';


@Injectable()
export class approveCorrectionService {

  constructor(private http: HttpClient) {
  }


  // Method to save user
  public approveCorrectionDta(data: approveCorrection): Observable<approveCorrection> {
    return this.http.post<approveCorrection>('api/adduser', data)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }


}