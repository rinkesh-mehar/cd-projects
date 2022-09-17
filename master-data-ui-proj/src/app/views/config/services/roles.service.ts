import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { Observable } from 'rxjs/internal/Observable';
import { catchError, retry } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { Role } from '../../acl/Models/role';

@Injectable({
  providedIn: 'root'
})
export class RolesService {

// Base url
baseUrl = environment.apiUrl + '/roles';
maxSize: number = 10;

constructor(private http: HttpClient) { }

// Http Headers
httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
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

 // GET
 getAllRoleList(): Observable<Role> {
  return this.http.get<Role>(this.baseUrl + '/list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}
}
