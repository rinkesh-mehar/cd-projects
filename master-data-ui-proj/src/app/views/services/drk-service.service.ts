import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {Observable, throwError} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class DrkServiceService {

    bugFixUrl = environment.apiUrl + '/drk/bug-fix?tableName=';

    constructor(private http: HttpClient) {
    }

    fixBug(tableName: string): Observable<any> {
        return this.http.get<any>(this.bugFixUrl + tableName)
            .pipe(
                retry(1),
                catchError(this.errorHandle)
            );
    }

    errorHandle(error) {
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
