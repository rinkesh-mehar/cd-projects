import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Rx';
import {HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { ServerResponse } from './server.response';
import { LoginModel } from './login.model';
import { environment } from '../../../../environments/environment';


@Injectable()
export class LoginService {

    response : ServerResponse;

    constructor(private http: HttpClient) {
    }

    public login( ) {
    }

        
    
     // Method to Login
     public loginUser(data : LoginModel): Observable<ServerResponse> {         
        return this.http.post<ServerResponse>(environment.baseURL+"login", data)
            .map((response) => response )
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }   

    

}