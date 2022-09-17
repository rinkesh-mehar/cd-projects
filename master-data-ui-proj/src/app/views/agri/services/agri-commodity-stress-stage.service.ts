import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError, pipe } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';
import { AgriCommodityStressStage } from '../models/AgriCommodityStressStage';
import { AgriCommodityService } from './agri-commodity.service';
import { AgriPhenophaseService } from './agri-phenophase.service';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { ZonalStressDurationService } from './zonal-stress-duration.service';
import { ZonalStressDuration } from '../models/ZonalStressDuration';
import { PageAgriCommodityStressStage } from '../models/PageAgriCommodityStressStage';
import { AgriStage } from '../../stress/model/AgriStage';

@Injectable({
  providedIn: 'root'
})
export class AgriCommodityStressStageService {

  // Base url
  baseUrl = environment.apiUrl+'/agri/commodity-stress-stage';
  maxSize: number = 10;

  constructor(private http: HttpClient, geoRegionService:GeoRegionService, agriCommodityService:AgriCommodityService,
     agriPhenophaseService:AgriPhenophaseService, agriStressService:ZonalStressDurationService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateCommodityStressStage(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }  

  // GET By ID
  GetStressStage(id): Observable<AgriCommodityStressStage> {
    return this.http.get<AgriCommodityStressStage>(this.baseUrl + '/' + id)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

      //For-Pagination 
      getPageAgriCommodityStressStage(page: number,rowSize: number, searchText, isValid: number,missing): Observable<PageAgriCommodityStressStage> {
        this.maxSize = rowSize || this.maxSize;
        const url = this.baseUrl + '?page=' + page + '&size=' + this.maxSize + '&isValid=' + isValid + '&searchText=' + searchText + '&missing=' + missing;
        return this.http.get<PageAgriCommodityStressStage>(url)
            .pipe(
                map(response => {
                    const data = response;
                    console.log(data.content);
                    return data;
                }));
    }

  //  //Get
  //  GetAllStressStageByRegionId(stateId):Observable<AgriStressStage>{
  //    return this.http.get<AgriStressStage>(this.baseUrl+ '/state-id/' + stateId)
  //    .pipe(
  //     retry(1),
  //     catchError(this.errorHandl)
  //   )
  //  }

  //Get
  GetAllStressStageByCommodityId(commodityId):Observable<AgriCommodityStressStage>{
   return this.http.get<AgriCommodityStressStage>(this.baseUrl+ '/commodity-id/' + commodityId)
   .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
 }

    //Get
   GetAllStressStageByPhenophaseId(phenophaseId):Observable<AgriCommodityStressStage>{
     return this.http.get<AgriCommodityStressStage>(this.baseUrl+ '/phenophase-id/' + phenophaseId)
     .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
   }

   GetAllStressStageByStressTypeId(stressTypeId):Observable<AgriCommodityStressStage>{
     return this.http.get<AgriCommodityStressStage>(this.baseUrl+'/stressType-id/'+stressTypeId)
     .pipe(
       retry(1),
       catchError(this.errorHandl)
     )
   }

   getStressStagByCommodityIdAndStressTypeId(commodityId, stressTypeId): Observable<ZonalStressDuration> {
    return this.http.get<ZonalStressDuration>(this.baseUrl + '/' + commodityId + '/' + stressTypeId + '/')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

   //Get
   GetAllStressStageByStressId(stressId):Observable<AgriCommodityStressStage>{
     return this.http.get<AgriCommodityStressStage>(this.baseUrl+ '/stress-id/' + stressId)
     .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
   }

  // GET
  GetAllStressStage(): Observable<AgriCommodityStressStage> {
    return this.http.get<AgriCommodityStressStage>(this.baseUrl+'/list')
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  // PUT
  UpdateCommodityStressStage(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteStressStage(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectStressStage(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeStressStage(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveStressStage(id) {
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

  getStageListByStressId(stressId):Observable<AgriStage>{
    return this.http.get<AgriStage>(this.baseUrl + '/getStageByStressId?stressId=' +  stressId)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }

  // Error handling
  errorHandl(error) {
     let errorMessage = '';
     if(error.error instanceof ErrorEvent) {
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
