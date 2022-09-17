import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ServerResponse } from '../user-login/server.response';
import {Observable} from 'rxjs/Rx';
import { environment } from '../../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class ForgotPasswordService {

  response : ServerResponse;

  constructor(private http: HttpClient) { }

  // Method to Add User
  public forgotPassword(data : {}): Observable<ServerResponse> {
    
    return this.http.post<ServerResponse>(environment.baseURL+'forgotPassword', data)
        .map((response) => response )
        .catch((error: any) => {
            return Observable.throw(error);
     });
  }


  


}
