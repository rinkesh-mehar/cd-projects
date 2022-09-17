import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { ZonalCommodity } from '../models/ZonalCommodity';
import { retry, catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { PageZonalCommodity } from '../models/PageZonalCommodity';
import { ResponseMessage } from '../../agri/models/ResponseMessage';

@Injectable({
  providedIn: 'root'
})
export class ZonalCommodityService {
  // Base url
  baseUrl = environment.apiUrl + '/zonal/commodity';
  maxSize: number = 10;


  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
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

// GET
getZonalCommodityListByAczId(aczId): Observable<ZonalCommodity> {
  return this.http.get<ZonalCommodity>(this.baseUrl + '/zonal-commodity-list-by-aczId/'+aczId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// GET
getStateCodeAczIdByZonalCommodityId(zonalCommodityId): Observable<ZonalCommodity> {
  return this.http.get<ZonalCommodity>(this.baseUrl + '/getStateCodeAczIdByZonalCommodityId/'+zonalCommodityId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// GET
getStateCodeAczIdZonalCommodityIdByZonalVarietyId(zonalVarietyId:number): Observable<ZonalCommodity> {
  return this.http.get<ZonalCommodity>(this.baseUrl + '/getStateCodeAczIdZonalCommodityIdByZonalVarietyId/'+zonalVarietyId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// GET
getCommodityIdByZonalCommodityId(zonalVarietyId:number): Observable<ZonalCommodity> {
  return this.http.get<ZonalCommodity>(this.baseUrl + '/getCommodityIdByZonalCommodityId/'+zonalVarietyId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// GET
getSowingHarvestWeekStartEndByZonalCommodityId(zonalCommodityId:number): Observable<ZonalCommodity> {
  return this.http.get<ZonalCommodity>(this.baseUrl + '/getSowingHarvestWeekStartEndByZonalCommodityId/'+zonalCommodityId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}


//GET
getZonalCommodityPagenatedList(page: number, rowSize: number, searchText): Observable<PageZonalCommodity> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageZonalCommodity>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}
 


// DELETE
DeleteCommodity(id) {
  return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

// Reject
rejectCommodity(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/reject', this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

//  Finalize
finalizeCommodity(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/final-approve', this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}


// Approve
approveCommodity(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/primary-approve', this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}


// GET By ID
getCommodityById(id): Observable<ZonalCommodity> {
  return this.http.get<ZonalCommodity>(this.baseUrl + '/' + id)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

// POST
  addZonalCommodity(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  //PUT
updateZonalCommodity(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrl  + '/'+ id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

getZonalCommodityByAczIDCommodityID(aczID:number,commodityID:number): Observable<ZonalCommodity> {
  return this.http.get<ZonalCommodity>(this.baseUrl + '/getZonalCommodityByAczIDCommodityID/'+aczID+'/'+commodityID)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}
}
