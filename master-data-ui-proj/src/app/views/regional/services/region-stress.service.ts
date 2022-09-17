import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';


import { GeoRegionService } from '../../geo/services/geo-region.service';
import { ZonalStressDuration } from '../../agri/models/ZonalStressDuration';
import { ZonalStressDurationService } from '../../agri/services/zonal-stress-duration.service';
import { RegionStress } from '../models/RegionStress';
import { GeoState } from '../../geo/models/GeoState';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { PageRegionStress } from '../models/PageRegionStress';


@Injectable({
  providedIn: 'root'
})
export class RegionStressService {

  // Base url
  baseUrl = environment.apiUrl + '/regional/stress';
  maxSize: number = 10;


  constructor(private http: HttpClient, geoRegionService: GeoRegionService, agriStressService: ZonalStressDurationService,
    geoStateService: GeoStateService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateStress(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetStress(id): Observable<RegionStress> {
    return this.http.get<RegionStress>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllStressByStateCode(stateCode): Observable<GeoState> {
    return this.http.get<GeoState>(this.baseUrl + '/state-stateCode/' + stateCode)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllStressByRegionId(regionId): Observable<RegionStress> {
    return this.http.get<RegionStress>(this.baseUrl + '/region-id/' + regionId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  //Get
  GetAllStressByStressId(StressId): Observable<ZonalStressDuration> {
    return this.http.get<ZonalStressDuration>(this.baseUrl + '/stress-id/' + StressId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }



  // GET
  GetAllStress(): Observable<RegionStress> {
    return this.http.get<RegionStress>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  getPageRegionStress(page: number, rowSize: number,searchText, isValid: number,missing): Observable<PageRegionStress> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing;
    return this.http.get<PageRegionStress>(url)
      .pipe(
        map(response => {
          const data = response;
          console.log(data.content);
          return data;
        }));
  }

  // PUT
  UpdateStress(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteStress(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectStress(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeStress(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveStress(id) {
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
