import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';
import { AgriCommodityService } from './agri-commodity.service';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { PageAgriQualityChart } from '../models/PageAgriQualityChart';
import { AgriQualityChart } from '../models/AgriQualityChart';
import { AgriHealthParameterService } from './agri-health-parameter.service';
import { AgriPhenophaseService } from './agri-phenophase.service';

@Injectable({
  providedIn: 'root'
})
export class AgriQualityChartService {

  // Base url
  baseUrl = environment.apiUrl + '/agri/quality-chart';
  maxSize: number = 10;
  


  constructor(private http: HttpClient, geoRegionService: GeoRegionService, agriCommodityService: AgriCommodityService,
    agriPhenophaseService : AgriPhenophaseService,agriHealthParameterService:AgriHealthParameterService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateQualityChart(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetQualityChart(id): Observable<AgriQualityChart> {
    return this.http.get<AgriQualityChart>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

//Get
  GetAllQualityChartByCommodityId(commodityId): Observable<AgriQualityChart> {
    return this.http.get<AgriQualityChart>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllQualityChartByPhenophaseId(phenophaseId): Observable<AgriQualityChart> {
    return this.http.get<AgriQualityChart>(this.baseUrl + '/phenophase-id/' + phenophaseId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllQualityChartByHealthParameterId(healthParameterId): Observable<AgriQualityChart> {
    return this.http.get<AgriQualityChart>(this.baseUrl + '/healthParameter-id/' + healthParameterId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // GET
  GetAllQualityChart(): Observable<AgriQualityChart> {
    return this.http.get<AgriQualityChart>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  getPageAgriQualityChart(page: number, rowSize: number,searchText,missing): Observable<PageAgriQualityChart> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize +  "&searchText=" + searchText +  "&missing=" + missing;
    return this.http.get<PageAgriQualityChart>(url)
      .pipe(
        map(response => {
          const data = response;
          //console.log(data.content);
          return data;
        }));
  }


  // PUT
  UpdateQualityChart(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteQualityChart(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Reject
  RejectQualityChart(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeQualityChart(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveQualityChart(id) {
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
