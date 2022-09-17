import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Rx';
import {HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { ServerResponse } from '../user-login/server.response';
import { ChangePasswordModel } from './change-password-model';
import { environment } from '../../../../environments/environment';


@Injectable()
export class ChangePasswordService {

    response : ServerResponse;

    constructor(private http: HttpClient) {
    }

     // Method to Login
     public changePassword(data : ChangePasswordModel): Observable<ServerResponse> {
         
        return this.http.post<ServerResponse>(environment.baseURL+'passwordChange', data)
            .map((response) => response )
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }
    
    // Method to Login
    public resetPassword(data : ChangePasswordModel): Observable<ServerResponse> {
        
       return this.http.post<ServerResponse>(environment.baseURL+'resetPassword', data)
           .map((response) => response )
           .catch((error: any) => {
               return Observable.throw(error);
           });
   }


}