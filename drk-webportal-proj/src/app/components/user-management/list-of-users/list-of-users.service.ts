import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AddUserModel } from '../add-user/user.model';
import { ServerResponse } from '../../password-management/user-login/server.response';
import {Observable} from 'rxjs/Rx';
import { ViewUserModel } from '../view-details/view-user-model';
import { environment } from '../../../../environments/environment';
import { EditUserModel } from '../edit-user/edit.user.model';

@Injectable({
  providedIn: 'root' 
})
export class ListOfUsersService {  

  response : ServerResponse;
  
  constructor(private http: HttpClient) { }


  public checkUserStatus(data) : Observable<Boolean> {

    return this.http.post<Boolean>(environment.baseURL+'userStatus', data)
        .map((response) => response )
        .catch((error: any) => {
            return Observable.throw(error);
    });


  }


  // Method to Add User
  public userList(data : {}): Observable<ServerResponse> {
    return this.http.post<ServerResponse>(environment.baseURL+'userList', data)
        .map((response) => response )
        .catch((error: any) => {
        return Observable.throw(error);
    });

  }

  // Method to Add User
  public addUser(data : AddUserModel): Observable<ServerResponse> {
   return this.http.post<ServerResponse>(environment.baseURL+'createuser', data)
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       });
  }

    // Method to Add User
    public updateUser(data : EditUserModel): Observable<ServerResponse> {
     
        return this.http.post<ServerResponse>(environment.baseURL+'updateUser', data)
            .map((response) => response )
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

  

  
  // Method to get User Role Name
  public getUserRoleName(data : {}): Observable<ServerResponse> {
      
    return this.http.post<ServerResponse>(environment.baseURL+'getUserRole', data)
     .map((response) => response )
     .catch((error: any) => {
         return Observable.throw(error);
     });
  }

  // Method to Activate User
  public activateUser(data : {}): Observable<ServerResponse> {
      return this.http.post<ServerResponse>(environment.baseURL+'userActive', data)
      .map((response) => response )
      .catch((error: any) => {
          return Observable.throw(error);
      });
  }

  // Method to In Activate User
  public inActivateUser(data : {}): Observable<ServerResponse> {
        return this.http.post<ServerResponse>(environment.baseURL+'userInActive', data)
        .map((response) => response )
        .catch((error: any) => {
            return Observable.throw(error);
        });
   }

     // Method to In Activate User
  public deleteUser(data : {}): Observable<ServerResponse> {
        return this.http.post<ServerResponse>(environment.baseURL+'deleteUser', data)
        .map((response) => response )
        .catch((error: any) => {
            return Observable.throw(error);
        });
    }


   // Method to In Activate User
  public viewUser(data : {}): Observable<ViewUserModel> {
     return this.http.post<any>(environment.baseURL+'viewUser', data)
        .map((response) => response )
        .catch((error: any) => {
            return Observable.throw(error);
        });
    }

    // Method to In Activate User
  public listOfReporting(data : {}): Observable<any[]> {
    return this.http.post<any>(environment.baseURL+'listOfreporting', data)
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       });
   }

     // Method to In Activate User
  public listOfReportingTo(data : {}): Observable<any[]> {
    return this.http.post<any>(environment.baseURL+'reportingTo', data)
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       });
   }

   public selfReportingTolist(data : {}): Observable<[]> {
    return this.http.post<any>(environment.baseURL+'selfReportingToList', data)
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       });
   }
   


   // Method to In Activate User
  public listOfStates(data : {}): Observable<any[]> {
    return this.http.post<any>(environment.baseURL+'states', data)
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       });
   }

    // Method to In Activate User
  public listOfRegion(data : {}): Observable<any[]> {
    return this.http.post<any>(environment.baseURL+'regions', data)
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       });
   }

   public getEcosystems(): Observable<any[]> {
    return this.http.get<any>(environment.baseURL+'ecosystems')
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       });
   }

   // Method to In Activate User
  public listOfRoles(data : {}): Observable<any[]> {
    return this.http.post<any>(environment.baseURL+'reportingRole', data)
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       });
   }

   // Method to Change roles
  public changeRoles(data : {}): Observable<any[]> {
    return this.http.post<any>(environment.baseURL+'changeRole', data)
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       });
   }

   public listofAssigningroles(data : {}): Observable<[]> {
    return this.http.post<any>(environment.baseURL+'listofAssigningroles', data)
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       }); 
   }

   public listofTileId(data : {}): Observable<[]> {
    return this.http.post<any>(environment.baseURL+'listoftileId', data)
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       });
   }


   public getRoleName(data : {}): Observable<ServerResponse> {
    return this.http.post<any>(environment.baseURL+'getRoleName', data)
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       });
   }

   public getEcoSystemName(data : {}): Observable<ServerResponse> {
    return this.http.post<any>(environment.baseURL+'getEcoSystemName', data)
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       });
   }

     // Method to In Activate User
  public saveUserRole(data : {}): Observable<ServerResponse> {
    return this.http.post<any>(environment.baseURL+'saveuserRole', data)
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       });
   }

   public testToken(data : {}): Observable<string> {
    
    return this.http.post<any>(environment.baseURL+'rest/test', data, {"headers": this.getHeaders()})
       .map((response) => response )
       .catch((error: any) => {
           return Observable.throw(error);
       });
   }

   getHeaders() {

    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Authorisation', 'Token eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOiI3NSIsImlhdCI6MTU3MzY2MjU2OSwiZXhwIjoxNTczNjYyNjI5fQ.X4isrlG4dHubUAnXmrnYBcj02FvaXxFQTk3vNA2tcd4Dva0GYDgAhGJpbEYLLL3lhepP8hQeykPnUXxQQ2hiew');
    
    return headers;
   }


}