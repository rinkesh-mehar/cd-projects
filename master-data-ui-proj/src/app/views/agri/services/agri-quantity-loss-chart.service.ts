import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { AgriQuantityLossChart } from '../models/AgriQuantityLossChart';
import { environment } from '../../../../environments/environment';
import { AgriCommodityService } from './agri-commodity.service';
import { ZonalStressDurationService } from './zonal-stress-duration.service';
import { AgriPhenophaseService } from './agri-phenophase.service';
import { PageAgriQuantityLossChart } from '../models/PageAgriQuantityLossChart';

@Injectable({
  providedIn: 'root'
})
export class AgriQuantityLossChartService {
 
  
  // Base url
  baseUrl = environment.apiUrl + '/agri/quantity-loss-chart';
  maxSize: number = 10;
  

  constructor(private http: HttpClient, commodityService: AgriCommodityService, agriStressService: ZonalStressDurationService,
    agriPhenophaseService:AgriPhenophaseService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateQuantityLossChart(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetQuantityLossChart(id): Observable<AgriQuantityLossChart> {
    return this.http.get<AgriQuantityLossChart>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

     //For-Pagination
  getPageAgriQuantityLossChart(page: number, rowSize: number, searchText, isValid: number, missing): Observable<PageAgriQuantityLossChart> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + '?page=' + page + '&size=' + this.maxSize + '&isValid=' + isValid + '&searchText=' + searchText + "&missing=" + missing;
    return this.http.get<PageAgriQuantityLossChart>(url)
        .pipe(
            map(response => {
              const data = response;
              console.log(data.content);
              return data;
            }));
  }

  // GET
  GetAllQuantityLossChart(): Observable<AgriQuantityLossChart> {
    return this.http.get<AgriQuantityLossChart>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllQuantityLossChartByCommodityId(commodityId): Observable<AgriQuantityLossChart> {
    return this.http.get<AgriQuantityLossChart>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllQuantityLossChartByStressId(varietyId): Observable<AgriQuantityLossChart> {
    return this.http.get<AgriQuantityLossChart>(this.baseUrl + '/stress-id/' + varietyId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllQuantityLossChartByPhenophaseId(phenophaseId): Observable<AgriQuantityLossChart> {
    return this.http.get<AgriQuantityLossChart>(this.baseUrl + '/phenophase-id/' + phenophaseId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // //Get
  // GetAllQuantityLossChartByCriteriaId(criteriaId): Observable<AgriQuantityLossChart> {
  //   return this.http.get<AgriQuantityLossChart>(this.baseUrl + '/criteria-id/' + criteriaId)
  //     .pipe(
  //       retry(1),
  //       catchError(this.errorHandl)
  //     )
  // }

  // PUT
  UpdateQuantityLossChart(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteQuantityLossChart(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  // Reject
  RejectQuantityLossChart(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeQuantityLossChart(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveQuantityLossChart(id) {
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
