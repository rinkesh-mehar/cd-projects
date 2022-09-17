import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';
import { AgriCommodityService } from './agri-commodity.service';
import { AgriPhenophaseService } from './agri-phenophase.service';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { AgriVarietyService } from './agri-variety.service';
import { AgriRemedialMeasures } from '../models/AgriRemedialMeasures';
import { AgriCommodityAgrochemicalService } from './agri-commodity-agrochemical.service';
import { PageAgriRemedialMeasures } from '../models/PageAgriRemedialMeasures';

@Injectable({
  providedIn: 'root'
})
export class AgriRemedialMeasuresService {

  // Base url
  baseUrl = environment.apiUrl + '/agri/agrochemical-brand';
  maxSize: number = 10;

  constructor(private http: HttpClient, geoRegionService: GeoRegionService, agriCommodityService: AgriCommodityService,
    agriPhenophaseService: AgriPhenophaseService, agriVarietyService: AgriVarietyService,
    agriAgroChemicalService: AgriCommodityAgrochemicalService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateRemedialMeasures(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetRemedialMeasures(id): Observable<AgriRemedialMeasures> {
    return this.http.get<AgriRemedialMeasures>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  getPageAgriRemedialMeasures(page: number, rowSize: number,searchText, isValid: number,missing): Observable<PageAgriRemedialMeasures> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing;
    return this.http.get<PageAgriRemedialMeasures>(url)
      .pipe(
        map(response => {
          const data = response;
          console.log(data.content);
          return data;
        }));
  }


  //Get
  GetAllRemedialMeasuresByCommodityId(commodityId): Observable<AgriRemedialMeasures> {
    return this.http.get<AgriRemedialMeasures>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllRemedialMeasuresByVarietyId(varietyId): Observable<AgriRemedialMeasures> {
    return this.http.get<AgriRemedialMeasures>(this.baseUrl + '/varietyId-id/' + varietyId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllRemedialMeasuresByAgrochemicalId(agrochemicalId): Observable<AgriRemedialMeasures> {
    return this.http.get<AgriRemedialMeasures>(this.baseUrl + '/agrochemicalId-id/' + agrochemicalId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllRemedialMeasures(): Observable<AgriRemedialMeasures> {
    return this.http.get<AgriRemedialMeasures>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateRemedialMeasures(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteRemedialMeasures(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Reject
  RejectRemedialMeasures(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeRemedialMeasures(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveRemedialMeasures(id) {
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
