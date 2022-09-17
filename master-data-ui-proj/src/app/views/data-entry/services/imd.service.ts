import { map, catchError, retry } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import {environment} from '../../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ResponseMessage } from '../../agri/models/ResponseMessage';

@Injectable({
  providedIn: 'root'
})
export class ImdService {

  baseUrls = environment.gstmModelURL + '/get';

  maxSize: number = 10;
  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
};

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

//GET
getImdDetails(stateId, districtId, blockId,year,month): Observable<any> {
  let url = this.baseUrls + '/imd?s=' + stateId + '&d=' + districtId + '&b=' + blockId + '&y=' + year + '&m=' + month;
  return this.http.get<any>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getStateList(): Observable<any> {
  let url = this.baseUrls + '/imd-state';
  return this.http.get<any>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getDistrictListByStateId(stateId): Observable<any> {
  let url = this.baseUrls + '/imd-district?s=' + stateId;
  return this.http.get<any>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getBlockListByStateIdAndDistrictId(stateId, districtId): Observable<any> {
  let url = this.baseUrls + '/imd-block?s=' + stateId + '&d=' + districtId;
  return this.http.get<any>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getYearListByStateIdAndDistrictIdAndBlockId(stateId, districtId, blockId): Observable<any> {
  let url = this.baseUrls + '/imd-year?s=' + stateId + '&d=' + districtId + '&b=' + blockId;
  return this.http.get<any>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}
//GET
getMonthListByStateIdAndDistrictIdAndBlockIdAndYear(stateId, districtId, blockId, year): Observable<any> {
  let url = this.baseUrls + '/imd-month?s=' + stateId + '&d=' + districtId + '&b=' + blockId + '&y=' + year;
  return this.http.get<any>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

}
