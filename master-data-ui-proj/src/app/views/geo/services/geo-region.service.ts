import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { GeoRegion } from '../models/GeoRegion';
import { environment } from '../../../../environments/environment';
import { GeoStateService } from './geo-state.service';
import { PageGeoRegion } from '../models/PageGeoRegion';


@Injectable({
  providedIn: 'root'
})
export class GeoRegionService {

  // Base url
  baseUrl = environment.apiUrl + '/geo/region';
  maxSize: number = 10;

  constructor(private http: HttpClient, geoStateService: GeoStateService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateRegion(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetRegion(regionId): Observable<GeoRegion> {
    return this.http.get<GeoRegion>(this.baseUrl + '/' + regionId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllRegion(): Observable<GeoRegion> {
    return this.http.get<GeoRegion>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllRegionByStateCode(stateCode): Observable<GeoRegion> {
    return this.http.get<GeoRegion>(this.baseUrl + '/state-code/' + stateCode)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  GetAllStateByRegionId(regionId): Observable<GeoRegion>{
    return this.http.get<GeoRegion>(this.baseUrl + '/region-id/' + regionId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }
 


  //For-Pagination
  getPageGeoRegion(page: number, rowSize: number,searchText): Observable<PageGeoRegion> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + "&searchText=" + searchText;
    return this.http.get<PageGeoRegion>(url)
      .pipe(
        map(response => {
          const data = response;
          //console.log(data.content);
          return data;
        }));
  }

  // PUT
  UpdateRegion(regionId, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + regionId + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteRegion(regionId) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + regionId + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectRegion(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  //  Finalize
  FinalizeRegion(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveRegion(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  getStateCodeByRegionId(regionId): Observable<GeoRegion>{
    return this.http.get<GeoRegion>(this.baseUrl + '/getStateCodeByRegionId/' + regionId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }


  // Error handling
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
