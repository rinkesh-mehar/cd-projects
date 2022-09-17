import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';
// import { GeoStateService } from '../../geo/services/geo-state.service';
// import { AgriSeasonService } from './agri-season.service';
// import { AgriVarietyService } from './agri-variety.service';
// import { AgriCommodityService } from './agri-commodity.service';
import { AgriBenchmarkVariety } from '../models/AgriBenchmarkVariety';
import { PageAgriBenchmarkVariety } from '../models/PageAgriBenchmarkVariety';
// import { GeoRegionService } from '../../geo/services/geo-region.service';
// import { PageAgriBenchmarkVariety } from '../models/PageAgriBenchmarkVariety';

@Injectable({
  providedIn: 'root'
})
export class AgriBenchmarkVarietyService {

  // Base url
  baseUrl = environment.apiUrl + '/agri/benchmark-variety';
  maxSize: number = 10;

  // constructor(private http: HttpClient, geoRegionService: GeoRegionService, geoStateService: GeoStateService, agriSeasonService: AgriSeasonService,
  //   commodityService: AgriCommodityService, agriVarietyService: AgriVarietyService, agriBenchmarkVariety: AgriBenchmarkVarietyService) { }

  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateBenchmarkVariety(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetBenchmarkVariety(id): Observable<AgriBenchmarkVariety> {
    return this.http.get<AgriBenchmarkVariety>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllBenchmarkVariety(): Observable<AgriBenchmarkVariety> {
    return this.http.get<AgriBenchmarkVariety>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  getPageAgriBenchmarkVariety(page: number,rowSize: number, searchText,missing): Observable<PageAgriBenchmarkVariety> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + "&searchText=" + searchText + "&missing=" + missing;
    return this.http.get<PageAgriBenchmarkVariety>(url)
      .pipe(
        map(response => {
          const data = response;
          console.log(data.content);
          return data;
        }));
  }

  //Get
  GetAlBenchmarkVarietyByRegionId(regionId): Observable<AgriBenchmarkVariety> {
    return this.http.get<AgriBenchmarkVariety>(this.baseUrl + '/region-regionId/' + regionId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllBenchmarkVarietyByStateCode(stateCode): Observable<AgriBenchmarkVariety> {
    return this.http.get<AgriBenchmarkVariety>(this.baseUrl + '/state-stateCode/' + stateCode)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  //Get
  GetAllBenchmarkVarietyBySeasonId(seasonId): Observable<AgriBenchmarkVariety> {
    return this.http.get<AgriBenchmarkVariety>(this.baseUrl + '/season-id/' + seasonId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllBenchmarkVarietyByCommodityId(commodityId): Observable<AgriBenchmarkVariety> {
    return this.http.get<AgriBenchmarkVariety>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllBenchmarkVarietyByVarietyId(varietyId): Observable<AgriBenchmarkVariety> {
    return this.http.get<AgriBenchmarkVariety>(this.baseUrl + '/variety-id/' + varietyId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateBenchmarkVariety(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteBenchmarkVariety(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  // Reject
  RejectBenchmarkVariety(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeBenchmarkVariety(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveBenchmarkVariety(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

   // POST
   moveToMaster(id): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/move-to-master/' + id, this.httpOptions)
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
