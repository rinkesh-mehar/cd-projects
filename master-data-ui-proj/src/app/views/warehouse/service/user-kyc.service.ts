import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {retry, catchError, map} from 'rxjs/operators';
import {environment} from '../../../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class UserKycService {

    baseUrl: string;

    constructor(private http: HttpClient) {
        this.baseUrl = environment.warehouseApiUrl + '/public';
    }

    GetAllRegion(): Observable<any> {
        return this.http.get(this.baseUrl + '/list');
    }

    // GTE
    getUserList(): Observable<any> {
        return this.http.get(this.baseUrl + '/user-list');
    }

    getPendingKycUserList() {
        return this.http.get<any>(this.baseUrl + '/user-list-pendingKyc');
    }

    // GTE
    getRejectedUserList(): Observable<any> {
        return this.http.get(this.baseUrl + '/user-list-rejected');
    }

    getUser(userId: string) {
        return this.http.get<any>(this.baseUrl + '/user-details/' + userId);
    }

    // post
    updateStatus(userId, value: any) {
        return this.http.post<any>(this.baseUrl + '/update-user/' + userId, value);
    }

    updateKycStatus(value: any) {
        return this.http.post<any>(this.baseUrl + '/update-user-kyc', value);
    }

    getSlot(warehouseId: string) {
        return this.http.get<any>(this.baseUrl + '/warehouse/slots/' + warehouseId)
            .pipe(
                retry(1)
            );
    }

    updateSlotKycStatus(value: any) {
        return this.http.post<any>(this.baseUrl + '/warehouse/slots-update' , value)
            .pipe(
                retry(1)
                )
    }
}
