import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { ResponseData } from './quality-assurance-list.model';
import { environment } from '../../../../../environments/environment';


@Injectable()
export class QualityAssuranceListService {

    constructor(private http: HttpClient) {
    }

    // kml detail existing
    public getQualityAssuranceListService(id): Observable<ResponseData> {
        return this.http.get<ResponseData>(environment.kmlQaURL + "getKmlqaList/" + id)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

}


