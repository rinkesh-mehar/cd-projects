import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';
import { AgriCommodityService } from './agri-commodity.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { AgriStressTypeService } from './agri-stress-type.service';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { ZonalStressDurationService } from './zonal-stress-duration.service';
import { AgriVarietyStress } from '../models/AgriVarietyStress';
import { PageAgriVarietyStress } from '../models/PageAgriVarietyStress';
import { AgriVarietyService } from './agri-variety.service';

@Injectable({
  providedIn: 'root'
})
export class AgriVarietyStressService {

  // Base url
  baseUrl = environment.apiUrl + '/agri/variety-stress';
  maxSize: number = 10;


  constructor(private http: HttpClient, geoStateServic: GeoStateService, geoRegionService: GeoRegionService, agriCommodityService: AgriCommodityService,
              agriStressTypeService: AgriStressTypeService, agriStressService: ZonalStressDurationService, agriVarietyService: AgriVarietyService) { }


  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateVarietyStress(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetVarietyStress(id): Observable<AgriVarietyStress> {
    return this.http.get<AgriVarietyStress>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllVarietyStressByCommodityId(commodityId): Observable<AgriVarietyStress> {
    return this.http.get<AgriVarietyStress>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllVarietyStressByRegionId(regionId): Observable<AgriVarietyStress> {
    return this.http.get<AgriVarietyStress>(this.baseUrl + '/region-id/' + regionId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllVarietyStressByStateId(stateId): Observable<AgriVarietyStress> {
    return this.http.get<AgriVarietyStress>(this.baseUrl + '/state-id/' + stateId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllVarietyStressByStressTypeId(stressTypeId): Observable<AgriVarietyStress> {
    return this.http.get<AgriVarietyStress>(this.baseUrl + '/stress-type-id/' + stressTypeId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllVarietyStressByStressId(boticStressId): Observable<AgriVarietyStress> {
    return this.http.get<AgriVarietyStress>(this.baseUrl + '/stress-id/' + boticStressId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllVarietyStressByVarietyId(varietyId): Observable<AgriVarietyStress> {
    return this.http.get<AgriVarietyStress>(this.baseUrl + '/variety-id/' + varietyId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllVarietyStress(): Observable<AgriVarietyStress> {
    return this.http.get<AgriVarietyStress>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  getPageAgriVarietyStress(page: number, rowSize: number, searchText, isValid: number,missing): Observable<PageAgriVarietyStress> {
    this.maxSize = rowSize || this.maxSize;
    console.log("missing : " + missing);
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing;
    return this.http.get<PageAgriVarietyStress>(url)
      .pipe(
        map(response => {
          const data = response;
          //console.log(data.content);
          return data;
        }));
  }

  // PUT
  UpdateVarietyStress(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteVarietyStress(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectVarietyStress(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeVarietyStress(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveVarietyStress(id) {
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
