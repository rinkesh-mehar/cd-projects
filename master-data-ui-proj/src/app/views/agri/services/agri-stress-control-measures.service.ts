import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { AgriStressControlMeasures } from '../models/AgriStressControlMeasures';
import { ZonalStressDuration } from '../models/ZonalStressDuration';
import { AgriCommodityService } from './agri-commodity.service';
import { ZonalStressDurationService } from './zonal-stress-duration.service';
import { AgriStressSeverityService } from './agri-stress-severity.service';
import { AgriControlMeasuresService } from './agri-control-measures.service';
import { PageAgriStressControlMeasures } from '../models/PageAgriStressControlMeasures';

@Injectable({
  providedIn: 'root'
})
export class AgriStressControlMeasuresService {

  baseUrl = environment.apiUrl + '/agri/stress-control-measures';
  maxSize: number = 10;

  constructor(private http: HttpClient,agriCommosityService : AgriCommodityService,agriStressService:ZonalStressDurationService,
    agriStressSeverityService:AgriStressSeverityService,agriStressControlMeasureService:AgriControlMeasuresService) { }


  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateAgriRecommendation(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetAgriRecommendation(id): Observable<AgriStressControlMeasures> {
    return this.http.get<AgriStressControlMeasures>(this.baseUrl + '/' + id)
      .pipe(
        catchError(this.errorHandl)
      )
  }


  // GET
  loadAllStressControlMeasures(): Observable<AgriStressControlMeasures> {
    return this.http.get<AgriStressControlMeasures>(this.baseUrl + '/list')
      .pipe(
        catchError(this.errorHandl)
      )
  }

   //For-Pagination
   getPageAgriStressControlMeasures(page: number, rowSize: number, searchText, isValid: number,missing,commodityId,stressId,stressSeverityId,controlMeasureId,filter): Observable<PageAgriStressControlMeasures> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" +  this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing + "&commodityId=" + commodityId + "&stressId=" + stressId  + "&stressSeverityId=" + stressSeverityId + "&controlMeasureId=" + controlMeasureId + "&filter=" + filter;
    return this.http.get<PageAgriStressControlMeasures>(url)
      .pipe(
        map(response => {
          const data = response;
          console.log(data.content);
          return data;
        }));
  }

  //Get
  GetAllRecommendationByCommodityId(commodityId): Observable<AgriStressControlMeasures> {
    return this.http.get<AgriStressControlMeasures>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllRecommendationByStressId(stressId): Observable<AgriStressControlMeasures> {
    return this.http.get<AgriStressControlMeasures>(this.baseUrl + '/stress-id/' + stressId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

   //get All Stress By Commodity
   GetAllStressByCommodity(commodityId):Observable<ZonalStressDuration>{
    return this.http.get<ZonalStressDuration>(this.baseUrl + '/getStressByCommodity?commodityId=' +  commodityId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  //Get
  GetAllRecommendationByStressSeverityId(stressSeverityId): Observable<AgriStressControlMeasures> {
    return this.http.get<AgriStressControlMeasures>(this.baseUrl + '/stressSeverity-id/' + stressSeverityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllRecommendationByStressControlMeasureId(stressControlMeasureId): Observable<AgriStressControlMeasures> {
    return this.http.get<AgriStressControlMeasures>(this.baseUrl + '/stressControlMeasure-id/' + stressControlMeasureId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateAgriRecommendation(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteAgriRecommendation(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectRecommendation(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeRecommendation(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveRecommendation(id) {
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
