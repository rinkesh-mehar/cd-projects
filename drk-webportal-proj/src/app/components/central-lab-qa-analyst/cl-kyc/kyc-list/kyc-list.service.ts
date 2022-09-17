import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { ResponseData } from './kyc-list.model';
import { environment } from '../../../../../environments/environment';


@Injectable()
export class KycListService {

    constructor(private http: HttpClient) {
    }

    // Method to save user
    public getKycListService(id): Observable<ResponseData> {
            return this.http.get<ResponseData>(environment.kycQaURL + 'getKycqaList/' + id)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }
}


