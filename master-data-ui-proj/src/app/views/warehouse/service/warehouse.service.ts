import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {retry} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class WarehouseService {

    baseUrl: string;

    constructor(private http: HttpClient) {
        this.baseUrl = environment.warehouseApiUrl + '/public';
    }

    // GTE
    getWarehouseList(): Observable<any> {
        return this.http.get(this.baseUrl + '/list-warehouse')
            .pipe(

            );
    }

    getWarehouse(warehouseId: any): Observable<any> {
        return this.http.get(this.baseUrl + '/warehouse-edit/' + warehouseId)
            .pipe(

            );
    }

    updateStatus(warehouseId: any, status: any, statusResponse: any): Observable<any> {
        let formData: FormData = new FormData;
        formData.append('status', status);
        formData.append('statusResponse', statusResponse);
        return this.http.post(this.baseUrl + '/warehouse-kycstatus/' + warehouseId, formData)
            .pipe(

            );
    }

}
