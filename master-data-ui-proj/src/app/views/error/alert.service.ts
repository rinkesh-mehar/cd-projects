import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { Router, NavigationStart } from '@angular/router';
import { SourceListMap } from 'source-list-map';

@Injectable({
    providedIn: 'root'
})
export class AlertService {

    private subject = new Subject<any>();
    private keepAfterRouteChange = false;
    constructor(private router: Router) {
        // clear alert messages on route change unless 'keepAfterRouteChange' flag is true
        this.router.events.subscribe(event => {
            if (event instanceof NavigationStart) {
                if (this.keepAfterRouteChange) {
                    // only keep for a single route change
                    this.keepAfterRouteChange = false;
                } else {
                    // clear alert message
                    this.clear();
                }
            }
        });
    }

    getAlert(): Observable<any> {
        return this.subject.asObservable();
    }

    success(message: any, keepAfterRouteChange = false) {
        this.keepAfterRouteChange = keepAfterRouteChange;
        message.type = 'success';
        this.subject.next(message);
    }

    error(message: any, keepAfterRouteChange = false) {
        this.keepAfterRouteChange = keepAfterRouteChange;
        message.type = 'error';
        this.subject.next(message);
    }

    clear() {
        this.subject.next();
    }
}
