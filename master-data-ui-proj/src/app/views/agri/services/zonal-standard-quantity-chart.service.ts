import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { ZonalStandardQuantityChart } from '../models/ZonalStandardQuantityChart';
import { environment } from '../../../../environments/environment';
import { AgriCommodityService } from './agri-commodity.service';
import { AgriVarietyService } from './agri-variety.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { PageZonalStandardQuantityChart } from '../models/PageZonalStandardQuantityChart';

@Injectable({
  providedIn: 'root'
})
export class ZonalStandardQuantityChartService {


  // Base url
  baseUrl = environment.apiUrl + '/zonal/standard-quantity-chart';
  maxSize: number = 10;

  constructor(private http: HttpClient, commodityService: AgriCommodityService, agriVarietyService: AgriVarietyService,
    geoStateService: GeoStateService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateStandardQuantityChart(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetStandardQuantityChart(id): Observable<ZonalStandardQuantityChart> {
    return this.http.get<ZonalStandardQuantityChart>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  getPageZonalStandardQuantityChart(page: number, rowSize: number, searchText, isValid: number, missing): Observable<PageZonalStandardQuantityChart> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing;
    return this.http.get<PageZonalStandardQuantityChart>(url)
      .pipe(
        map(response => {
          const data = response;
          console.log(data.content);
          return data;
        }));
  }

  // GET
  GetAllStandardQuantityChart(): Observable<ZonalStandardQuantityChart> {
    return this.http.get<ZonalStandardQuantityChart>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllStandardQuantityChartByCommodityId(commodityId): Observable<ZonalStandardQuantityChart> {
    return this.http.get<ZonalStandardQuantityChart>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllStandardQuantityChartByVarietyId(varietyId): Observable<ZonalStandardQuantityChart> {
    return this.http.get<ZonalStandardQuantityChart>(this.baseUrl + '/variety-id/' + varietyId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllStandardQuantityChartByStateCode(StateCode): Observable<ZonalStandardQuantityChart> {
    return this.http.get<ZonalStandardQuantityChart>(this.baseUrl + '/state-stateCode/' + StateCode)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateStandardQuantityChart(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteStandardQuantityChart(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  // Reject
  RejectStandardQuantityChart(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeStandardQuantityChart(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveStandardQuantityChart(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
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

  moveToMaster(id): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/move-to-master/' + id, this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

}