import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';
import { GeoStateService } from '../../geo/services/geo-state.service';
import { AgriSeasonService } from './agri-season.service';
import { AgriVarietyService } from './agri-variety.service';
import { AgriCommodityService } from './agri-commodity.service';
import { AgriPhenophaseService } from './agri-phenophase.service';
import { ZonalPhenophaseDuration } from '../models/ZonalPhenophaseDuration';
import { PageZonalPhenophaseDuration } from '../models/PageZonalPhenophaseDuration';


@Injectable({
  providedIn: 'root'
})
export class ZonalPhenophaseDurationService {

  baseUrl = environment.apiUrl + '/zonal/phenophase-duration';
  maxSize: number = 10;

  constructor(private http: HttpClient, geoStateService: GeoStateService, agriSeasonService: AgriSeasonService,
    agriVarietyService: AgriVarietyService, agriCommodityService: AgriCommodityService, agriPhenophaseService: AgriPhenophaseService) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }


  // POST
  CreatePhenophaseDuration(data): Observable<ResponseMessage> {

    // let formData: FormData = new FormData;
    // if (file) {
    //   formData.append("image", file);
    // }
    // formData.append("data", JSON.stringify(data));

    return this.http.post<ResponseMessage>(this.baseUrl + '/add',JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetPhenophaseDuration(id): Observable<ZonalPhenophaseDuration> {
    return this.http.get<ZonalPhenophaseDuration>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetPhenophaseDurationByStateId(stateCode): Observable<ZonalPhenophaseDuration> {
    return this.http.get<ZonalPhenophaseDuration>(this.baseUrl + '/state-stateCode/' + stateCode)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetPhenophaseDurationByCommodityId(commodityId): Observable<ZonalPhenophaseDuration> {
    return this.http.get<ZonalPhenophaseDuration>(this.baseUrl + '/commodity-id' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetPhenophaseDurationBySeasonId(seasonId): Observable<ZonalPhenophaseDuration> {
    return this.http.get<ZonalPhenophaseDuration>(this.baseUrl + '/season-id' + seasonId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetPhenophaseDurationByVarietyId(varietyId): Observable<ZonalPhenophaseDuration> {
    return this.http.get<ZonalPhenophaseDuration>(this.baseUrl + '/variety-id' + varietyId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetPhenophaseDurationByPhenophaseId(phenophaseId): Observable<ZonalPhenophaseDuration> {
    return this.http.get<ZonalPhenophaseDuration>(this.baseUrl + '/phenophase-id' + phenophaseId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get All
  GetAllPhenophaseDuration(): Observable<ResponseMessage> {
    return this.http.get<ResponseMessage>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


   //For-Pagination
   getPageZonalPhenophaseDuration(page: number, rowSize: number, searchText, isValid: number,missing): Observable<PageZonalPhenophaseDuration> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing;
    return this.http.get<PageZonalPhenophaseDuration>(url)
        .pipe(
            map(response => {
                const data = response;
                //console.log(data.content);
                return data;
            }));
}

  //Update 
  UpdateZonalPhenophaseDuration(id, data): Observable<ResponseMessage> {
    // let formData: FormData = new FormData;
    // if (file) {
    //   formData.append("image", file);
    // }
    // formData.append("data", JSON.stringify(data));
    return this.http.put<ResponseMessage>(this.baseUrl + '/' + id + '/update', JSON.stringify(data),this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }





  // PUT
  UpdateVarietyStress(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Delete
  DeletePhenophaseDuration(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + '/' + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectPhenophaseDuration(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizePhenophaseDuration(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApprovePhenophaseDuration(id) {
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
