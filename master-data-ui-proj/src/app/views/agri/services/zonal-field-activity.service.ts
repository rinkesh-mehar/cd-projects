import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';
import { AgriCommodityService } from './agri-commodity.service';
import { AgriPhenophaseService } from './agri-phenophase.service';
// import { GeoRegionService } from '../../geo/services/geo-region.service';
import { ZonalFieldActivity } from '../models/ZonalFieldActivity';
import { AgriSeasonService } from './agri-season.service';
import { PageZonalFieldActivity } from '../models/PageZonalFieldActivity';

@Injectable({
  providedIn: 'root'
})
export class ZonalFieldActivityService {

  // Base url
  baseUrl = environment.apiUrl + '/zonal/field-activity';
  maxSize: number = 10;

  constructor(private http: HttpClient, agriCommodityService: AgriCommodityService,
    agriPhenophaseService: AgriPhenophaseService, agriSeasonService: AgriSeasonService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateFieldActivity(data, uploadedImage: any): Observable<ResponseMessage> {
    const formData: FormData = new FormData;
        console.log("uploadedLogo : " + uploadedImage);
       if (uploadedImage != undefined){

        formData.append('imageFile', uploadedImage);
       }
       formData.append('data', JSON.stringify(data));
        return this.http.post<ResponseMessage>(this.baseUrl + '/add',formData)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetFieldActivity(id): Observable<ZonalFieldActivity> {
    return this.http.get<ZonalFieldActivity>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  getPageAgriFieldActivity(page: number, rowSize: number, searchText, isValid: number,missing): Observable<PageZonalFieldActivity> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing;
    return this.http.get<PageZonalFieldActivity>(url)
      .pipe(
        map(response => {
          const data = response;
          console.log(data.content);
          return data;
        }));
  }

  // //Get
  // GetAllFieldActivityByRegionId(regionId): Observable<AgriFieldActivity> {
  //   return this.http.get<AgriFieldActivity>(this.baseUrl + '/region-id/' + regionId)
  //     .pipe(
  //       retry(1),
  //       catchError(this.errorHandl)
  //     )
  // }

  //Get
  GetAllFieldActivityByCommodityId(commodityId): Observable<ZonalFieldActivity> {
    return this.http.get<ZonalFieldActivity>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllFieldActivityBySeasonId(varietyId): Observable<ZonalFieldActivity> {
    return this.http.get<ZonalFieldActivity>(this.baseUrl + '/seasonId-id/' + varietyId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllFieldActivityByPhenophaseId(phenophaseId): Observable<ZonalFieldActivity> {
    return this.http.get<ZonalFieldActivity>(this.baseUrl + '/phenophase-id/' + phenophaseId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllFieldActivity(): Observable<ZonalFieldActivity> {
    return this.http.get<ZonalFieldActivity>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateFieldActivity(id, data, uploadedImage: any): Observable<ResponseMessage> {
    const formData: FormData = new FormData;
    if (uploadedImage != undefined){

        formData.append('imageFile', uploadedImage);
    }
    formData.append('data', JSON.stringify(data));
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', formData)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteFieldActivity(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectFieldActivity(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeFieldActivity(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveFieldActivity(id) {
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
