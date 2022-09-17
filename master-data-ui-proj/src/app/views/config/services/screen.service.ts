import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ScreenService {

// Base url
baseUrl = environment.apiUrl + '/screen';
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
 getAllScreenList(): Observable<Screen> {
  return this.http.get<Screen>(this.baseUrl + '/list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}


}
