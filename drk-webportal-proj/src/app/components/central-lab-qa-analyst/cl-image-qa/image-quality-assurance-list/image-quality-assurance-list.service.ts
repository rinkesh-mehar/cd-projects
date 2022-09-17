import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { ResponseData } from './image-quality-assurance-list.model';
import { environment } from '../../../../../environments/environment';


@Injectable()
export class ImageQualityAssuranceList {

    constructor(private http: HttpClient) {
    }

    // Method to save user
    public getImageQualityAssuranceListService(id): Observable<ResponseData> {
        return this.http.get<ResponseData>(environment.ImageQaURL + 'getIqaList/' + id)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }
}

