import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { ResponseMessage } from './sample-diagnosis-approval-list.model';
import { environment } from '../../../../../environments/environment';


@Injectable()
export class sampleDiagnosisService {

    constructor(private http: HttpClient) {
    }


    // Method to get user info
    public getsampleDiagnosisService(param): Observable<ResponseMessage> {
        return this.http.get<any>(environment.rltURL + 'getDiagnosisApprovalList/' + localStorage.getItem('loginUserid'), { params: param })
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

    public assignTask(data): Observable<ResponseMessage> {
        return this.http.post<any>(environment.rltURL + 'assignTask', data)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }
}