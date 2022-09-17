import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { environment } from '../../../../environments/environment';


@Injectable({
    providedIn: 'root'
})
export class TokenService {
    constructor(private http: HttpClient) { }

    public getRefreshToken(): Observable<string> {
        let data = {"userName":localStorage.getItem('loginUsername'), "id":localStorage.getItem('loginUserid'), "role":localStorage.getItem('loginRoleName')}
        return this.http.post(environment.baseURL + "token", data, { responseType: "text" })
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }
}