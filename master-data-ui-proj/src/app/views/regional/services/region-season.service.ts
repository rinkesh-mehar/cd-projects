import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { RegionSeason } from '../models/RegionSeason';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { AgriSeasonService } from '../../agri/services/agri-season.service';
import { environment } from '../../../../environments/environment';
import { PageRegionSeason } from '../models/PageRegionSeason';
import { GeoStateService } from '../../geo/services/geo-state.service';


@Injectable({
  providedIn: 'root'
})
export class RegionSeasonService {

    // Base url
    baseUrl = environment.apiUrl+'/regional/season';
    maxSize: number = 10;
    

    constructor(private http: HttpClient, geoStateService:GeoStateService, agriSeasonService:AgriSeasonService) { }
  
    // Http Headers
    httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
  
    // POST
    CreateSeason(data): Observable<ResponseMessage> {
      return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }  
  
    // GET By ID
    GetSeason(id): Observable<RegionSeason> {
      return this.http.get<RegionSeason>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // GET
    GetAllSeason(): Observable<RegionSeason> {
      return this.http.get<RegionSeason>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  //For-Pagination
  getPageRegionSeason(page: number, rowSize: number,searchText, isValid: number, missing): Observable<PageRegionSeason> {
    this.maxSize = rowSize || this.maxSize;
    console.log("missing : " + missing);
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing;
    return this.http.get<PageRegionSeason>(url)
      .pipe(
        map(response => {
          const data = response;
          //console.log(data.content);
          return data;
    }));
  }
  
    // PUT
    UpdateSeason(id, data): Observable<ResponseMessage> {
      return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // DELETE
    DeleteSeason(id){
      return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id +  '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }


  // Reject
  RejectSeason(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeSeason(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveSeason(id) {
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
