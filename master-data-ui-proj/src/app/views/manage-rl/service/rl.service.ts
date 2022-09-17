import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { retry, catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../agri/models/ResponseMessage';
import { RlUserpage } from '../model/rl-userpage';

@Injectable({
  providedIn: 'root'
})
export class RlService {

  constructor(private http: HttpClient) { }
  baseUrl: string = environment.apiUrl + '/site/rluser';

  maxSize: number = 10;

  httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
};


  uploadExcelFile(file: any, roleName: string) {
    let formData: FormData = new FormData;
    if (file) {
      formData.append("file", file);
      formData.append("roleName", roleName);
    }

    return this.http.post<ResponseMessage>(this.baseUrl + '/import-data', formData)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );
  }

  getRlUserList(page: number, rowSize: number, searchText: string): Observable<RlUserpage> {
    this.maxSize = rowSize || this.maxSize;
    let url = this.baseUrl + '/list' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
    return this.http.get<RlUserpage>(url)
      .pipe(
        map(response => {
          const data = response;
          return data;
        }));
  }


  storeUser(data: any): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add-rluser', data)
      .pipe(
        catchError(this.errorHandl)
      );
  }

  updateRlUser(id: any, data: any, bankData: any, userImage: File, aadharImage: File, panImage: File, drivingImage: File, accountImage: File): Observable<ResponseMessage> {
    const formData: FormData = new FormData;
    if (userImage != undefined) {
      formData.append('userProfileImage', userImage);
    }
    if (aadharImage != undefined) {
      formData.append('userAadharImage', aadharImage);
    }
    if (panImage != undefined) {
      formData.append('userPanImage', panImage);
    }
    if (drivingImage != undefined) {
      formData.append('userDLImage', drivingImage);
    }
    if (accountImage != undefined) {
      formData.append('accountImage', accountImage);
    }
    formData.append('data', JSON.stringify(data));
    formData.append('bankDetails', JSON.stringify(bankData));
    return this.http.post<ResponseMessage>(this.baseUrl + '/update-rl/' + id, formData)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );
  }

  getRlManageId(id: string): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/get-user/' + id)
      .pipe(
        catchError(this.errorHandl)
      );
  }

  listOfPageNumbers(): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/total-pages')
      .pipe(
        catchError(this.errorHandl)
      );
  }

  exportRlDataToExcel(pageNo: number) {
    // window.location.href = this.baseUrl + '/download?page=' + pageNo;
    return this.http.get<any>(this.baseUrl + '/download?page=' + pageNo,{responseType: 'blob' as 'json'})
    .pipe(
          catchError(this.errorHandl)
        );
  }

  // Error handling
  errorHandl(error: any) {
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

   // GET
   getAllGeoRegion(): Observable<any> {
    return this.http.get(this.baseUrl + '/region-list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  // PUT
  reActiveLink(id): Observable<any> {
    console.log("Inside Reactive : " + id);
  return this.http.put<ResponseMessage>(this.baseUrl + '/re-activate-link/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

}
