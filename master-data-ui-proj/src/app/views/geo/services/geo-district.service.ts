import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { GeoDistrict } from '../models/GeoDistrict';
import { GeoStateService } from './geo-state.service';
import { environment } from '../../../../environments/environment';
import { PageGeoDistrict } from '../models/PageGeoDistrict';

import {DistrictAlias} from '../geo-district-alias/model/DistrictAlias';
import {Alias} from '../geo-district-alias/model/Alias';
import {Districts} from '../geo-district-alias/model/Districts';
import {GeoState} from '../models/GeoState';
import {States} from '../geo-state-alias/model/States';
@Injectable({
  providedIn: 'root'
})
export class GeoDistrictService {


  // Base url
  baseUrl = environment.apiUrl + '/geo/district';
  baseUrls = environment.apiUrl;
  currentUser: any = {};
  maxSize: number = 10;


  constructor(private http: HttpClient,
    geoStateService: GeoStateService) {
    this.currentUser = JSON.parse(sessionStorage.getItem('currentUser'));

  }


  canDelete() {
    return false;
  }

  canEdit() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 2 || this.currentUser.roleId == 3 || this.currentUser.roleId == 4)
  }

  canPrimaryApprove() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 3)
  }
  canFinalize() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 2)
  }

  canReject() {
    return (this.currentUser.roleId == 1 || this.currentUser.roleId == 2 || this.currentUser.roleId == 3)
  }


  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateDistrict(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetDistrict(id): Observable<GeoDistrict> {
    return this.http.get<GeoDistrict>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllDistrict(): Observable<GeoDistrict> {
    return this.http.get<GeoDistrict>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllDistrictByStateCode(stateCode): Observable<GeoDistrict> {
    return this.http.get<GeoDistrict>(this.baseUrl + '/state-code/' + stateCode)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  getPageGeoDistrict(page: number, rowSize: number,searchText): Observable<PageGeoDistrict> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + "&searchText=" + searchText;
    return this.http.get<PageGeoDistrict>(url)
      .pipe(
        map(response => {
          const data = response;
          //console.log(data.content);
          return data;
        }));
  }

  // PUT
  UpdateDistrict(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectDistrict(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteDistrict(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // DELETE
  FinalizeDistrict(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // DELETE
  ApproveDistrict(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  GetAllDistrictAliasByStateCode(stateCode): Observable<any> {
    return this.http.get<any>(this.baseUrl + '/state-code/' + stateCode)
        .pipe(
            retry(1),
            catchError(this.errorHandl)
        );
  }
  // GET
  getAllDistrictAlias(): Observable<Alias> {
    return this.http.get<Alias>(this.baseUrl + '/alias')
        .pipe(
            retry(1),
            catchError(this.errorHandl)
        )
  }

  // POST
  saveAliasDistrict(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/alias/tag', JSON.stringify(data), this.httpOptions)
        .pipe(
            retry(1),
            catchError(this.errorHandl)
        )
  }
  //For-Pagination
  getPageGeoDistrictAlias(page: number): Observable<any> {
    var url = this.baseUrl + '/alias/page' +"?page=" + page + "&size=" + environment.pageSize;
    return this.http.get<any>(url)
        .pipe(
            map(response => {
              const data = response;
              //console.log(data.content);
              return data;
            }));
  }

  GetAllState(): Observable<any> {
    return this.http.get<any>(this.baseUrls + '/geo/state' + '/total/list')
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
