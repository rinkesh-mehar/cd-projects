import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';



@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  getNotficationCount(userId: number) {
    return this.http.get(environment.notificationApiUrl + '/user-notification-count/' + userId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    );
  }

  getAllNotificationsByUserId(userId: number): Observable<any> { // Only showing views = 0
    return this.http.get(environment.notificationApiUrl + '/user-notification/' + userId);
  }

  getNotificationById(notificationId: number): Observable<any> {
    return this.http.get(environment.notificationApiUrl + '/notification/' + notificationId);
  }

  getAllNotification(userId: number): Observable<any> {
    return this.http.get(environment.notificationApiUrl + '/all-notification/' + userId);
  }

  updateViewNotificationStatus(userId: number, notificationId: number) {
    return this.http.post(environment.notificationApiUrl + '/update-notification-status/' + userId + '/' + notificationId, this.httpOptions);
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
}
