import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { environment } from '../../../../environments/environment';

@Injectable()
export class AssignmentListService {

    constructor(private http: HttpClient) {
    }

    // Method to Submit AssignVillages
    public setAssignmentListBYPrmId(prmId: number): Observable<any> {
        return this.http.get<any>(environment.prmURL +'getassignmentlist/'+ prmId)
          .map((response) => response)
          .catch((error: any) => {
            return Observable.throw(error);
        });
    }

    

}