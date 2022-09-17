import { PageGeoState } from './../models/PageGeoState';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {retry, catchError, map} from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { GeoState } from '../models/GeoState';
import { GeoCountryService } from './geo-country.service';
import { environment } from '../../../../environments/environment';
import {CommodityAlias} from '../../agri/add-agri-commodity-alias/model/CommodityAlias';
import {AgriVariety} from '../../agri/models/Agrivariety';


@Injectable({
  providedIn: 'root'
})
export class GeoStateService {

  // Base url
  baseUrl = environment.apiUrl + '/geo/state';
  baseUrls = environment.apiUrl;
  maxSize: number = 10;

  constructor(private http: HttpClient, geoCountryService: GeoCountryService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateState(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetState(id): Observable<GeoState> {
    return this.http.get<GeoState>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllState(): Observable<GeoState> {
    return this.http.get<GeoState>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  getStateWiseRegion(id): Observable<any>  {
    return this.http.get<any>(this.baseUrls + '/geo/region/state-code/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  getStateWiseCommodity(id) {
    return this.http.get<any>(this.baseUrls + 'agri/commodity/commodity/?stateCode='+id.target.value)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  getTotalState(): Observable<GeoState>{
    return this.http.get<GeoState>(this.baseUrl + '/total/list')
        .pipe(
            retry(1),
            catchError(this.errorHandl)
        )
  }

//GET
getStatePagenatedList(page: number, rowSize: number, searchText): Observable<PageGeoState> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageGeoState>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

  // PUT
  UpdateState(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteState(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectState(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeState(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveState(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

/*  getAllStateAlias(): Observable<StateAlias> {
    return this.http.get<StateAlias>(this.baseUrl + '/alias')
        .pipe(
            retry(1),
            catchError(this.errorHandl)
        );
  }*/

//For-Pagination
  getPageGeoStateAlias(page: number): Observable<any> {
    var url = this.baseUrl + '/alias/page' +"?page=" + page + "&size=" + environment.pageSize;
    return this.http.get<any>(url)
        .pipe(
            map(response => {
              const data = response;
              //console.log(data.content);
              return data;
            }));
  }

  // POST
  saveAliasState(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/alias/tag', JSON.stringify(data), this.httpOptions)
        .pipe(
            retry(1),
            catchError(this.errorHandl)
        );
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
