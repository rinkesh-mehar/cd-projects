import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { AuthenticationService } from './authentication.service';
import { Router } from '@angular/router';
import { AlertService } from '../error/alert.service';
import { log } from 'util';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService, private router: Router, public alertService: AlertService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            console.log('err', err);
            if (err.status === 401) {
                // auto logout if 401 response returned from api
                this.authenticationService.logout();
                location.reload(true);
            }

            if (err.status === 0) {
                err.error = {};
                err.error.status = 0;
                err.error.errorCode = 'CDTERR-000';
                err.error.error = "Server Unavailable!";
                err.error.details = "Could to reached to requested server either connection is very slow or sever is unavialbe to respond!";
                // this.router.navigateByUrl('error/' + JSON.stringify(err.error));
            } else if (err.status === 403) {
                err.error.status = err.status;
                // this.router.navigateByUrl('error/' + JSON.stringify(err.error));
                // } else if (err.status === 500) {
                //     this.router.navigateByUrl('500');
            } else if (err.error && (err.error.errorCode = 'CDTERR-006')) {
                err.error.status = err.status
                // this.router.navigateByUrl('error/' + JSON.stringify(err.error).replace(/\//g, ','));
            } else {
                if (!err.error) {
                    err.error = err;
                }
            }
            this.alertService.error(err.error);

            return throwError(err.error);
            // const error = err.error.message || err.statusText;
            // return throwError(error);
        }))
    }
}