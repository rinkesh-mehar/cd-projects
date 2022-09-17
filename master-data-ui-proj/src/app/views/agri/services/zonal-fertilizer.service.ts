import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map, filter } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';
import { AgriCommodityService } from './agri-commodity.service';
import { GeoRegionService } from '../../geo/services/geo-region.service';

import { ZonalFertilizer } from '../models/ZonalFertiliizer';
import { GeneralUomService } from '../../general/services/general-uom.service';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { AgriSeasonService } from './agri-season.service';
import { AgriEcosystemService } from './agri-ecosystem.service';
import { AgriDoseFactorService } from './agri-dose-factor.service';
import { PageZonalFertilizer } from '../models/PageZonalFertilizer';

@Injectable({
  providedIn: 'root'
})
export class ZonalFertilizerService {


  // Base url
  baseUrl = environment.apiUrl + '/zonal/fertilizer';
  maxSize: number = 10;
  


  constructor(private http: HttpClient, geoRegionService: GeoRegionService, agriCommodityService: AgriCommodityService,geoStateService:GeoStateService,
    generalUomService:GeneralUomService,agriSeasonService:AgriSeasonService,agriDoseFactorService : AgriDoseFactorService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateFertilizer(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetFertilizer(id): Observable<ZonalFertilizer> {
    return this.http.get<ZonalFertilizer>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

    //Get
    GetAllFertilizerByStateCode(stateCode): Observable<ZonalFertilizer> {
      return this.http.get<ZonalFertilizer>(this.baseUrl + '/state-stateCode/' + stateCode)
        .pipe(
          retry(1),
          catchError(this.errorHandl)
        )
    }

  //Get
  GetAllFertilizerByRegionId(regionId): Observable<ZonalFertilizer> {
    return this.http.get<ZonalFertilizer>(this.baseUrl + '/region-id/' + regionId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  //get
  GetAllFertilizerBySeasonId(seasonId): Observable<ZonalFertilizer> {
    return this.http.get<ZonalFertilizer>(this.baseUrl + '/season-id/' + seasonId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllFertilizerByCommodityId(commodityId): Observable<ZonalFertilizer> {
    return this.http.get<ZonalFertilizer>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllFertilizerByDoseFactorId(doseFactorId): Observable<ZonalFertilizer> {
    return this.http.get<ZonalFertilizer>(this.baseUrl + '/doseFactor-id/' + doseFactorId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllFertilizerByUomId(uomId): Observable<ZonalFertilizer> {
    return this.http.get<ZonalFertilizer>(this.baseUrl + '/uom-id/' + uomId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllFertilizer(): Observable<ZonalFertilizer> {
    return this.http.get<ZonalFertilizer>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  getPageAgriFertilizer(page: number, rowSize: number,searchText, isValid: number,missing,stateCode,seasonId,doseFactorId,commodityId,name,filter): Observable<PageZonalFertilizer> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid +  "&searchText=" + searchText +  "&stateCode=" + stateCode +  "&seasonId=" + seasonId +  "&doseFactorId=" + doseFactorId +  "&commodityId=" + commodityId +  "&name=" + name +  "&filter=" + filter;
    return this.http.get<PageZonalFertilizer>(url)
      .pipe(
        map(response => {
          const data = response;
          //console.log(data.content);
          return data;
        }));
  }


  // PUT
  UpdateFertilizer(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteFertilizer(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Reject
  RejectFertilizer(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeFertilizer(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveFertilizer(id) {
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
