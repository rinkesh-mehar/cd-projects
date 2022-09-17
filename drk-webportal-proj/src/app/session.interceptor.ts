import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, EMPTY } from 'rxjs';
import { Router } from '@angular/router'

@Injectable()
export class SessionInterceptor implements HttpInterceptor {

    constructor(private router:Router){}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        if(request.url.indexOf('login') >= 0 
        || request.url.indexOf('token') >= 0 
        || request.url.indexOf('forgotPassword') >= 0) {
            return next.handle(request);
        } else {
            try {
                if(localStorage.getItem("session") != null) {
                    let session = JSON.parse(localStorage.getItem("session"))
                    if(session['st'] > new Date().getTime()) {
                        session['st'] = new Date().getTime()+(1000*60*60);
                        localStorage.setItem('session', JSON.stringify(session));
                        request = request.clone({
                            setHeaders: {
                              Authorization: 'Bearer '+session['at']
                            }
                          });
                          return next.handle(request);
                    } else {
                      localStorage.clear();
                      sessionStorage.clear();
                      this.router.navigate(['/'])
                      return EMPTY;
                    }
                  } else {
                    localStorage.clear();
                    sessionStorage.clear();
                    this.router.navigate(['/'])
                    return EMPTY;
                  }
            } catch(err) {
                localStorage.clear()
                this.router.navigate(['/'])
                return EMPTY;
            }
        }
      }

}