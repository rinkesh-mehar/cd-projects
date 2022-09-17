import { Injectable } from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpResponse} from '@angular/common/http';
import {Observable, pipe} from 'rxjs';

import { AuthenticationService } from './authentication.service';
import { environment } from '../../../environments/environment';
import { Router , RouterStateSnapshot} from '@angular/router';
import {LoaderService} from './loader.service';
import {tap} from 'rxjs/operators';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService, private router: Router, private loaderService: LoaderService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        let ok: string;
        this.loaderService.isLoading.next(true);
        // add authorization header with jwt token if available
        let currentUser = this.authenticationService.currentUserValue;

        // console.log("Current User: "+currentUser);
        if (!this.authenticationService.isUserLoggedIn){
            this.authenticationService.logout();
            // this.router.navigate(['/login']);
        }

        if (currentUser && currentUser.token) {
            request = request.clone({
                setHeaders: {
                    auth: `Bearer ${currentUser.token}`
                }
            });
        }

        //add allow origin
        request = request.clone({
            setHeaders: {
                'Allow-Origin' : environment.allowOrigin,
                'Access-Control-Allow-Origin' : environment.allowOrigin,
            }
        });

        // console.log("REQ Allow-Origin: "+request.headers.get('Allow-Origin'));
        // console.log("REQ Access-Control-Allow-Origin: "+request.headers.get('Access-Control-Allow-Origin'));
        // console.log("REQ Authorization: "+request.headers.get('Authorization'));
        // console.log("URL : "+request.url);
        return next.handle(request)
    .pipe(
            tap(
                event => ok = event instanceof HttpResponse ? `${this.loaderService.isLoading.next(false)}` : '',
                // Operation failed; error is an HttpErrorResponse
                error => {
                    this.loaderService.isLoading.next(false)
                }
            )

        );
    }
}
