import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpEvent, HttpClient, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import 'rxjs/Rx';
import { ResponseList } from './dr-krishi-cctc-technical.model';
import { environment } from '../../../../../../environments/environment';


@Injectable()
export class DrKrishiTechnicalService {

    constructor(private http: HttpClient) {
    }

    // Dr Krishi Non Technical list
    public getDrKrishiTechnicalService(): Observable<ResponseList> {
        return this.http.get<any>(environment.cctcURL+'fieldMonitoringList/'+localStorage.getItem('loginUserid'))
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }


  public getCallingStatusList(): Observable<any> {
    return this.http.get<any>(environment.cctcURL + 'getCallingStatusList/')
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }
}


