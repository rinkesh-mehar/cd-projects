import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { AgriStressControlRecommendation } from '../models/AgriStressControlRecommendation';
import { AgriCommodityService } from './agri-commodity.service';
import { ZonalStressDurationService } from './zonal-stress-duration.service';
import { AgriStressSeverityService } from './agri-stress-severity.service';
import { AgriControlMeasuresService } from './agri-control-measures.service';
import { AgriStressTypeService } from './agri-stress-type.service';
import { AgriCommodityAgrochemicalService } from './agri-commodity-agrochemical.service';
import { AgriAgrochemicalTypeService } from './agri-agrochemical-type.service';
import { GeneralUomService } from '../../general/services/general-uom.service';
import { PageAgriStressControlRecommendation } from '../models/PageAgriStressControlRecommendation';

@Injectable({
  providedIn: 'root'
})
export class ZonalStressControlRecommendationService {

  baseUrl = environment.apiUrl + '/zonal/stress-control-recommendation';
  maxSize: number = 10;

  constructor(private http: HttpClient, private agriCommosityService: AgriCommodityService, private agriStressService: ZonalStressDurationService,
              private agriStressSeverityService: AgriStressSeverityService, private agriStressControlMeasureService: AgriControlMeasuresService,
              private agriStressTypeService: AgriStressTypeService, private agriAgrochemicalService: AgriCommodityAgrochemicalService, private agriAgrochemicalTypeService:
      AgriAgrochemicalTypeService, private generalUomService: GeneralUomService) { }


  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateAgriStressControlRecommendation(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetAgriStressControlRecommendation(id): Observable<AgriStressControlRecommendation> {
    return this.http.get<AgriStressControlRecommendation>(this.baseUrl + '/' + id)
      .pipe(
        catchError(this.errorHandl)
      )
  }


  // GET
  GetAllAgriStressControlRecommendation(): Observable<AgriStressControlRecommendation> {
    return this.http.get<AgriStressControlRecommendation>(this.baseUrl + '/list')
      .pipe(
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  getPageAgriStressControlRecommendation(page: number, rowSize: number, searchText, isValid: number,missing,stateCode,districtCode,commodityId,controlMeasureId,stressId,recomendationID,agrochemicalId,filter): Observable<PageAgriStressControlRecommendation> {
    this.maxSize = rowSize || this.maxSize;
    console.log("missing : " + missing);
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing + "&stateCode=" + stateCode + "&districtCode=" + districtCode + "&commodityId=" + commodityId + "&controlMeasureId=" + controlMeasureId + "&stressId=" + stressId + "&recomendationID=" + recomendationID + "&agrochemicalId=" + agrochemicalId + "&filter=" + filter;
    return this.http.get<PageAgriStressControlRecommendation>(url)
      .pipe(
        map(response => {
          const data = response;
          console.log(data.content);
          return data;
        }));
  }


  //Get
  GetAllStressControlRecommendationByCommodityId(commodityId): Observable<AgriStressControlRecommendation> {
    return this.http.get<AgriStressControlRecommendation>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllStressControlRecommendationByStressId(stressId): Observable<AgriStressControlRecommendation> {
    return this.http.get<AgriStressControlRecommendation>(this.baseUrl + '/stress-id/' + stressId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllStressControlRecommendationByStressSeverityId(stressSeverityId): Observable<AgriStressControlRecommendation> {
    return this.http.get<AgriStressControlRecommendation>(this.baseUrl + '/stressSeverity-id/' + stressSeverityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllStressControlRecommendationByStressControlMeasureId(stressControlMeasureId): Observable<AgriStressControlRecommendation> {
    return this.http.get<AgriStressControlRecommendation>(this.baseUrl + '/stressControlMeasure-id/' + stressControlMeasureId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllStressControlRecommendationByStressTypeId(stressTypeId): Observable<AgriStressControlRecommendation> {
    return this.http.get<AgriStressControlRecommendation>(this.baseUrl + '/stressType-id/' + stressTypeId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllStressControlRecommendationByUomId(uomId): Observable<AgriStressControlRecommendation> {
    return this.http.get<AgriStressControlRecommendation>(this.baseUrl + '/uom-id/' + uomId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllStressControlRecommendationByAgrochemicalId(agrochemicalId): Observable<AgriStressControlRecommendation> {
    return this.http.get<AgriStressControlRecommendation>(this.baseUrl + '/agrochemical-id/' + agrochemicalId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllStressControlRecommendationByAgrochemicalTypeId(agrochemicalTypeId): Observable<AgriStressControlRecommendation> {
    return this.http.get<AgriStressControlRecommendation>(this.baseUrl + '/agrochemicalType-id/' + agrochemicalTypeId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateAgriStressControlRecommendation(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteAgriStressControlRecommendation(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectStressControlRecommendation(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeStressControlRecommendation(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveStressControlRecommendation(id) {
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
