import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { ResponseMessage } from './manage-hardware.model';
import { environment } from '../../../../../environments/environment';


@Injectable()
export class ManageHardwareService {

    constructor(private http: HttpClient) {
    }

    // Method to get Non Technical List
    public getManageHardwarelist(userId, searchKeyward): Observable<ResponseMessage> {
        return this.http.get<any>(environment.rltURL + 'assignedHardwareList/' + userId, { params: { "searchKeyward": searchKeyward } })
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

    public unTag(data): Observable<ResponseMessage> {
        return this.http.post<any>(environment.rltURL + 'unTagHardware/', data)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

}