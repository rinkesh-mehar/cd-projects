import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient} from '@angular/common/http';
import 'rxjs/Rx';
import { KmlDetails, ResponseData } from './kml-details.model';
import { environment } from '../../../../../environments/environment';



@Injectable()
export class KmlDetailsService {

  constructor(private http: HttpClient) {
  }

  // kml detail existing
  public getKmlDetailsService(id): Observable<ResponseData> {
    return this.http.get<ResponseData>(environment.kmlQaURL + 'fileDetails/' + id)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

  public downloadFile(name): Observable<any> {
    const options = new RequestOptions({responseType: ResponseContentType.Blob });
    return this.http.get<any>(environment.kmlQaURL+'downloadFile/'+name,{responseType: 'arraybuffer' as 'json'}).map((response) => {
      return response
    })
      .catch((error: any) => {
        return Observable.throw(error);
      });

  }


  public uploadKmlFile(formData): Observable<ResponseData> {
    return this.http.post<ResponseData>(environment.kmlQaURL + 'uploadFile', formData)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }


  //submitdetails/{taskId}/{area}

  public submitKmlDetailsService(taskId, area, userId): Observable<ResponseData> {
    return this.http.get<ResponseData>(environment.kmlQaURL + 'submitdetails/' + taskId + '/' + area + '/' + userId)
      .map((response) => response)
      .catch((error: any) => {
        return Observable.throw(error);
      });
  }

}


