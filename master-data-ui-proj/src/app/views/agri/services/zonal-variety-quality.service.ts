import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { environment } from '../../../../environments/environment';
import { AgriCommodityService } from './agri-commodity.service';
import { AgriVarietyService } from './agri-variety.service';
import { ZonalVarietyQuality } from '../models/ZonalVarietyQuality';
import { PageZonalVarietyQuality } from '../models/PageZonalVarietyQuality';


@Injectable({
  providedIn: 'root'
})
export class ZonalVarietyQualityService {

  // Base url
  baseUrl = environment.apiUrl + '/zonal/variety-quality';
  maxSize: number = 10;

  constructor(private http: HttpClient, commodityService: AgriCommodityService, agriVarietyService: AgriVarietyService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateVarietyQuality(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetVarietyQuality(id): Observable<ZonalVarietyQuality> {
    return this.http.get<ZonalVarietyQuality>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllVarietyQuality(): Observable<ZonalVarietyQuality> {
    return this.http.get<ZonalVarietyQuality>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

    //For-Pagination
    getPageAgriVarietyQuality(page: number,rowSize: number,searchText, isValid: number,missing): Observable<PageZonalVarietyQuality> {
      this.maxSize = rowSize || this.maxSize;
      var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid +"&searchText=" + searchText +"&missing=" + missing;
      return this.http.get<PageZonalVarietyQuality>(url)
        .pipe(
          map(response => {
            const data = response;
            console.log(data.content);
            return data;
          }));
    }

  //Get
  GetAllarietyQualityByCommodityId(commodityId): Observable<ZonalVarietyQuality> {
    return this.http.get<ZonalVarietyQuality>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllarietyQualityByVarietyId(varietyId): Observable<ZonalVarietyQuality> {
    return this.http.get<ZonalVarietyQuality>(this.baseUrl + '/variety-id/' + varietyId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateVarietyQuality(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteVarietyQuality(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  // Reject
  RejectVarietyQuality(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeVarietyQuality(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveVarietyQuality(id) {
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
