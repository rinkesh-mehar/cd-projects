import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { environment } from '../../../../../environments/environment';


@Injectable()
export class RightsListService {

    constructor(private http: HttpClient) {
    }


    public searchData(searchcaseId): Observable<any> {
        return this.http.get<any>(environment.rightInfoURL +"rightInfo/"+searchcaseId)
            .map((response) => response)
            .catch((error: any) => {
                return Observable.throw(error);
            });
    }

}