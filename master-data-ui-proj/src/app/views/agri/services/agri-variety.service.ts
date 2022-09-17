import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { AgriVariety } from '../models/Agrivariety';
import { AgriCommodityService } from './agri-commodity.service';
import { environment } from '../../../../environments/environment';
import { PageAgriVariety } from '../models/PageAgriVariety';



@Injectable({
  providedIn: 'root'
})
export class AgriVarietyService {

  // Base url
  baseUrl = environment.apiUrl + '/agri/variety';
  maxSize: number = 10;


  constructor(private http: HttpClient, commodityService: AgriCommodityService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateVariety(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetVariety(id): Observable<AgriVariety> {
    return this.http.get<AgriVariety>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // GET
  GetAllVarieties(): Observable<AgriVariety> {
    return this.http.get<AgriVariety>(this.baseUrl + '/list')
      .pipe(
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  getPageAgriVariety(page: number,rowSize: number,searchText, isValid: number,missing,commodityId,hsCodeId,domesticRestrictions,internationalRestrictions,filter): Observable<PageAgriVariety> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing + "&commodityId=" + commodityId + "&hsCodeId=" + hsCodeId + "&domesticRestrictions=" + domesticRestrictions + "&internationalRestrictions=" + internationalRestrictions + "&filter=" + filter;
    return this.http.get<PageAgriVariety>(url)
      .pipe(
        map(response => {
          const data = response;
          //console.log(data.content);
          return data;
        }));
  }

  //Get
  GetAllVarietyByCommodityId(commodityId): Observable<AgriVariety> {
    return this.http.get<AgriVariety>(this.baseUrl +'/'+ commodityId + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get variety by State and Commodity
  getAllVarietyByStateAndCommodity(stateCode: number, commodityId: number): Observable<AgriVariety> {
    return this.http.get<AgriVariety>(this.baseUrl + '/' + stateCode + '/' + commodityId + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      );
  }

  //Get
  getVarietyListByStateCodeDiscrictCodeSeasonIdAndCommodityId(stateCode:number,districtCode:number,seasonId:number,commodityId:number): Observable<AgriVariety> {
    return this.http.get<AgriVariety>(this.baseUrl +'/'+ stateCode +'/'+ districtCode +'/'+ seasonId +'/'+ commodityId + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllVarietyByMspGroupId(mspGroupId): Observable<AgriVariety> {
    return this.http.get<AgriVariety>(this.baseUrl +'/'+ mspGroupId + '/list')
        .pipe(
            retry(1),
            catchError(this.errorHandl)
        )
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
  DeleteVariety(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
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
  FinalizeVariety(id) {
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
getVarietyListByCommodityId(commodityId): Observable<AgriVariety> {
  return this.http.get<AgriVariety>(this.baseUrl+'/variety-list-by-commodityId/' + commodityId)
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
