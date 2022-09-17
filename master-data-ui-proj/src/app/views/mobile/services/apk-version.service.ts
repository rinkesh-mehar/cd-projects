import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { AppVersionDetails } from '../../mobile/AppVersionDetails';
import {retry, catchError, map} from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../agri/models/ResponseMessage';
import {VersionList} from '../apk-version-control-list/pagination/versionList';

@Injectable({
  providedIn: 'root'
})
export class ApkVersionService {

  // baseUrls = 'http://192.168.0.47:8080'
  baseUrls = environment.apkVersionApiURL;

  constructor(private http: HttpClient) {
  }

  httpOptions = {
      headers: new HttpHeaders({
          'Content-Type': 'application/json'
      })
  };
  getVersionWithPage(page: number, searchText): Observable<VersionList> {
    let url = this.baseUrls + '/get-all-version-paginated' + '?page=' + page + '&size=' + environment.pageSize + '&searchText=' + searchText;
    return this.http.get<VersionList>(url)
        .pipe(
            map(response => {
              const data = response;
              return data;
            }));
  }
  getAllApp(): Observable<AppVersionDetails> {
    return this.http.get<AppVersionDetails>(this.baseUrls +'/get-all-version')
        .pipe(
            retry(1),
            catchError(this.errorHandl)
        );
}


addNewApp(data,apkUrl,keyStore,encryptedKey): Observable<ResponseMessage> {

  const formData: FormData = new FormData;

  if(apkUrl != undefined || apkUrl != null){
    formData.append("apkUrl",apkUrl);
  }
  if(keyStore != undefined || keyStore != null){
    formData.append("keyStoreFile",keyStore);
  }
  if(encryptedKey != undefined || encryptedKey != null){
    formData.append("encryptedKey",encryptedKey);
  }

    formData.append("data",JSON.stringify(data));

    const headers = new HttpHeaders({
      // 'Content-Type': 'multipart/form-data'
  });

  return this.http.post<ResponseMessage>(this.baseUrls + '/add', formData,{headers})
}


updateNewApp(id,data,apkUrl,keyStore,encryptedKey): Observable<ResponseMessage> {
  
  const formData: FormData = new FormData;

  if(apkUrl != undefined || apkUrl != null){
    formData.append("apkUrl",apkUrl);
  }
  if(keyStore != undefined || keyStore != null){
    formData.append("keyStoreFile",keyStore);
  }
  if(encryptedKey != undefined || encryptedKey != null){
    formData.append("encryptedKey",encryptedKey);
  }

    formData.append("data",JSON.stringify(data));

    const headers = new HttpHeaders({
      // 'Content-Type': 'multipart/form-data'
  });

  return this.http.put<ResponseMessage>(this.baseUrls + '/update/'+ id, formData,{headers})
      .pipe(
          catchError(this.errorHandl)
      );
}

getApkById(id): Observable<AppVersionDetails> {
  return this.http.get<AppVersionDetails>(this.baseUrls + '/getApkById' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

errorHandl(error) {
  let errorMessage = '';
  if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
  } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
  }
  console.log(errorMessage);
  return throwError(errorMessage);
}

}




