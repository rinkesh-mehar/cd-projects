import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { HttpEvent, HttpClient, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import 'rxjs/Rx';
import { AssignRoleRlm } from './assign-role-rlm.model';


@Injectable()
export class assignRoleRlmService {

  constructor(private http: HttpClient) {
  }


  // Method to save user
  public assignUserRoleRlm(data: AssignRoleRlm): Observable<AssignRoleRlm> {
    return this.http.post<AssignRoleRlm>('api/adduser', data)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }


}