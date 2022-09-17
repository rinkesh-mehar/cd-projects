import { ResponseMessage } from './../../farmer/models/ResponseMessage';
import {environment} from '../../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { PageCropdataCalendar } from '../models/page-cropdata-calendar';

@Injectable({
  providedIn: 'root'
})
export class EventCalendarService {

  baseUrls = environment.apiUrl + '/site/holiday-calendar';

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

// POST
addHoliday(data): Observable<ResponseMessage> {
console.log("from service : " + data.holidayDate);
  return this.http.post<ResponseMessage>(this.baseUrls + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

 // GET
 getAllGeoRegion(): Observable<any> {
  return this.http.get(this.baseUrls + '/region-list')
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

//GET
getHolidayListByPagenation(page: number, rowSize: number, searchText): Observable<PageCropdataCalendar> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageCropdataCalendar>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

//GET
getHolidayListByRegionId(page: number, rowSize: number, regionId: number, searchText): Observable<PageCropdataCalendar> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrls + '/holiday-list-by-regionId' + '?page=' + page + '&size=' + this.maxSize + '&regionId=' + regionId + '&searchText=' + searchText;
  return this.http.get<PageCropdataCalendar>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

getHolidayDateList(regionId: number): Observable<any> {
  return this.http.get<any>(this.baseUrls + '/holiday-date-list'+ '?regionId=' + regionId)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

//GET
checkHolidayAlreadyExist(regionId: number, holidayDate : any): Observable<any> {
  return this.http.get<any>(this.baseUrls + '/check-holiday-exist'+ '?regionId=' + regionId + '&holidayDate=' + holidayDate)
      .pipe(
          catchError(this.errorHandl)
      );
}

 // PUT
 activateHoliday(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/activate-holiday/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
deactivateHoliday(id): Observable<any> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/deactivate-holiday/' + id  , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// PUT
updateHoliday(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrls + '/update/' + id , JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

}
