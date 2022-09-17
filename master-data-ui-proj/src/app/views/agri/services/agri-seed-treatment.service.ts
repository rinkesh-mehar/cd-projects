import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { ResponseMessage } from '../models/ResponseMessage';
import { AgriSeedTreatment } from '../models/AgriSeedTreatment';
import { environment } from '../../../../environments/environment';
import { AgriCommodityService } from './agri-commodity.service';
import { AgriVarietyService } from './agri-variety.service';
import { GeneralUomService } from '../../general/services/general-uom.service';
import { PageAgriSeedTreatment } from '../models/PageAgriSeedTreatment';

@Injectable({
  providedIn: 'root'
})
export class AgriSeedTreatmentService {

  // Base url
  baseUrl = environment.apiUrl + '/agri/seed-treatment';
  maxSize: number = 10;

  constructor(private http: HttpClient, commodityService: AgriCommodityService, agriVarietyService: AgriVarietyService,
    generalUomService:GeneralUomService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  // POST
  CreateSeedTreatment(data): Observable<ResponseMessage> {
    return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID
  GetSeedTreatment(id): Observable<AgriSeedTreatment> {
    return this.http.get<AgriSeedTreatment>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllSeedTreatment(): Observable<AgriSeedTreatment> {
    return this.http.get<AgriSeedTreatment>(this.baseUrl+'/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

    //For-Pagination
    getPageAgriSeedTreatment(page: number, rowSize: number, searchText, isValid: number,missing,commodityId,varietyId,name,filter): Observable<PageAgriSeedTreatment> {
      this.maxSize = rowSize || this.maxSize;
      var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing + "&commodityId=" + commodityId + "&varietyId=" + varietyId + "&name=" + name + "&filter=" + filter;
      return this.http.get<PageAgriSeedTreatment>(url)
        .pipe(
          map(response => {
            const data = response;
            console.log(data.content);
            return data;
          }));
    }

  //Get
  GetAllSeedTreatmenByCommodityId(commodityId): Observable<AgriSeedTreatment> {
    return this.http.get<AgriSeedTreatment>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllSeedTreatmenByVarietyId(varietyId): Observable<AgriSeedTreatment> {
    return this.http.get<AgriSeedTreatment>(this.baseUrl + '/variety-id/' + varietyId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllSeedTreatmenByUomId(uomId): Observable<AgriSeedTreatment> {
    return this.http.get<AgriSeedTreatment>(this.baseUrl + '/uom-id/' + uomId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // PUT
  UpdateSeedTreatment(id, data): Observable<ResponseMessage> {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', JSON.stringify(data), this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteSeedTreatment(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }
  // Reject
  RejectSeedTreatment(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeSeedTreatment(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveSeedTreatment(id) {
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
