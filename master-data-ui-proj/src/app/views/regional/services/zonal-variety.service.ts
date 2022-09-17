import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { ZonalVariety } from '../models/ZonalVariety';
import { GeoRegionService } from '../../geo/services/geo-region.service';
import { AgriCommodityService } from '../../agri/services/agri-commodity.service';
import { AgriVarietyService } from '../../agri/services/agri-variety.service';
import { environment } from '../../../../environments/environment';
import { PageZonalVariety } from '../models/PageZonalVariety';
import { GeoStateService } from '../../geo/services/geo-state.service';


@Injectable({
  providedIn: 'root'
})
export class ZonalVarietyService {

    // Base url
    baseUrl = environment.apiUrl+'/zonal/variety';
    maxSize: number = 10;
    
    constructor(private http: HttpClient,
      geoRegionService : GeoRegionService,
      commodityService : AgriCommodityService,
      geoStateService : GeoStateService,
      agriVarietyService : AgriVarietyService) { }
  
    // Http Headers
    httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
  
    // POST
    CreateVariety(data): Observable<ResponseMessage> {
      // alert(JSON.stringify(data));
      console.log("Inside service");
      return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }  
  
    // GET By ID
    GetVariety(id): Observable<ZonalVariety> {
      return this.http.get<ZonalVariety>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // GET
    GetAllVariety(): Observable<ZonalVariety> {
      return this.http.get<ZonalVariety>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }

    getPageZonalVariety(page: number, rowSize: number,searchText, isValid: number,missing,stateCode,seasonId,commodityId,varietyId,filter): Observable<PageZonalVariety> {
      // console.log("whereClause from sevice : "+  whereClause);
      this.maxSize = rowSize || this.maxSize;
      console.log("missing : " + missing);
      var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing + "&stateCode=" + stateCode + "&seasonId=" + seasonId  + "&commodityId=" + commodityId + "&varietyId=" + varietyId + "&filter=" + filter;
      console.log("url : "+  url);
      return this.http.get<PageZonalVariety>(url)
        .pipe(
          map(response => {
            const data = response;
            //console.log(data.content);
            return data;
      }));
    }
  
    // PUT
    UpdateVariety(id, data): Observable<ResponseMessage> {
      return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }
  
    // DELETE
    DeleteVariety(id){
      return this.http.delete<ResponseMessage>(this.baseUrl +"/" + id +  '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
    }

      // Reject
  RejectVariety(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeVarietys(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveVariety(id) {
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

// GET
getZonalVarietyListByZonalCommodityId(zonalcommodityId): Observable<ZonalVariety> {
  return this.http.get<ZonalVariety>(this.baseUrl+'/zonal-variety-list-by-zonalcommodityId/' + zonalcommodityId)
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

// GET
getZonalVarietyIdByStateCodeAczIdCommodityIdPhenophaseId(stateCode, aczId,commodityId,phenophaseId): Observable<ZonalVariety> {
  return this.http.get<ZonalVariety>(this.baseUrl+'/getZonalVarietyIdByStateCodeAczIdCommodityIdPhenophaseId/' + stateCode + '/' + aczId + '/' + commodityId + '/' + phenophaseId)
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
