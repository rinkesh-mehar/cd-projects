import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { RegionSeason } from '../models/RegionSeason';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { AgriSeasonService } from '../../agri/services/agri-season.service';
import { environment } from '../../../../environments/environment';
import { PageRegionSeasonCommodity } from '../models/PageRegionSeasonCommodity';
import { RegionSeasonCommodity } from '../models/RegionSeasonCommodity';

@Injectable({
  providedIn: 'root'
})

export class RegionSeasonCommodityService {

  // Base url
  baseUrl = environment.apiUrl + '/regional/commodity';
  maxSize: number = 10;


  constructor(
    private http: HttpClient,
    geoRegionService: GeoRegionService,
    agriSeasonService: AgriSeasonService
  ) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateSeasonCommodity(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetSeasonCommodity(id): Observable<RegionSeasonCommodity> {
    return this.http.get<RegionSeasonCommodity>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllSeasonCommodity(): Observable<RegionSeasonCommodity> {
    return this.http.get<RegionSeasonCommodity>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  //For-Pagination
  getPageRegionSeasonCommodity(page: number, rowSize: number,searchText, isValid: number, missing): Observable<PageRegionSeasonCommodity> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid  + "&searchText=" + searchText  + "&missing=" + missing;
    return this.http.get<PageRegionSeasonCommodity>(url)
      .pipe(
        map(response => {
          const data = response;
          console.log(data.content);
          return data;
        }));
  }

  // PUT
  UpdateSeasonCommodity(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteSeasonCommodity(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Reject
  RejectSeasonCommodity(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeSeasonCommodity(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveSeasonCommodity(id) {
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
