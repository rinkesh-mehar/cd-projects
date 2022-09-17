import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { IndividualKycDetails, ResponseData } from './individual-kyc-details.model';
import { environment } from '../../../../../environments/environment';


@Injectable()
export class IndividualKycDetailsService {

    constructor(private http: HttpClient) {
    }

    // Method to save user
    public submitIndividualKycDetails(data: IndividualKycDetails): Observable<ResponseData> {
        return this.http.post<ResponseData>(environment.kycQaURL + 'saveKycDetails', data)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

    // Method to save user
    public getIndividualKycDetailsData(farmerId, taskId, userId): Observable<ResponseData> {
        return this.http.get<ResponseData>(environment.kycQaURL + 'getKycDetails/' + farmerId + '/' + taskId + '/' + userId)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

}
