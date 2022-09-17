import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError, map } from 'rxjs/operators';
import { environment } from '../../../../environments/environment';
import { ResponseMessage } from '../../general/models/ResponseMessage';
import { AgriCommodityService } from './agri-commodity.service';
import { AgriPhenophaseService } from './agri-phenophase.service';
import { AgriPlantPartService } from './agri-plant-part.service';
import { AgriCommodityPlantPart } from '../models/AgriCommodityPlantPart';
import { PageAgriCommodityPlantPart } from '../models/PageAgriCommodityPlantPart';


@Injectable({
  providedIn: 'root'
})
export class AgriCommodityPlantPartService {

  // Base url
  baseUrl = environment.apiUrl + '/agri/commodity-plant-part';
  maxSize: number = 10;


  constructor(private http: HttpClient, agriCommodityService: AgriCommodityService,
    agriPhenophaseService: AgriPhenophaseService, agriPlantPartService: AgriPlantPartService) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }


  // POST
  CreateCommodityPlantPart(data, file): Observable<ResponseMessage> {
    let formData: FormData = new FormData;
    if (file) {
      formData.append("image", file);
    }
    formData.append("data", JSON.stringify(data));

    return this.http.post<ResponseMessage>(this.baseUrl + '/add', formData)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET By ID 
  GetCommodityPlantPart(id): Observable<AgriCommodityPlantPart> {
    return this.http.get<AgriCommodityPlantPart>(this.baseUrl + '/' + id)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllCommodityPlantPartByCommodityId(commodityId): Observable<AgriCommodityPlantPart> {
    return this.http.get<AgriCommodityPlantPart>(this.baseUrl + '/commodity-id/' + commodityId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllCommodityPlantPartByPhenophaseId(phenophaseId): Observable<AgriCommodityPlantPart> {
    return this.http.get<AgriCommodityPlantPart>(this.baseUrl + '/phenophase-id/' + phenophaseId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //Get
  GetAllCommodityPlantPartByPlantPartId(plantPartId): Observable<AgriCommodityPlantPart> {
    return this.http.get<AgriCommodityPlantPart>(this.baseUrl + '/plant-part-id/' + plantPartId)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // GET
  GetAllCommodityPlantPart(): Observable<AgriCommodityPlantPart> {
    return this.http.get<AgriCommodityPlantPart>(this.baseUrl + '/list')
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //For-Pagination
  getPageAgriCommodityPlantPart(page: number, rowSize: number, searchText, isValid: number,missing): Observable<PageAgriCommodityPlantPart> {
    this.maxSize = rowSize || this.maxSize;
    var url = this.baseUrl + "?page=" + page + "&size=" + this.maxSize + '&isValid=' + isValid + "&searchText=" + searchText + "&missing=" + missing;
    return this.http.get<PageAgriCommodityPlantPart>(url)
      .pipe(
        map(response => {
          const data = response;
          //console.log(data.content);
          return data;
        }));
  }

  // PUT
  UpdateCommodityPlantPart(id, data, file): Observable<ResponseMessage> {

    let formData: FormData = new FormData;
    if (file) {
      formData.append("image", file);
    }
    formData.append("data", JSON.stringify(data));


    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/update', formData)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // DELETE
  DeleteCommodityPlantPart(id) {
    return this.http.delete<ResponseMessage>(this.baseUrl + "/" + id + '/delete', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  // Reject
  RejectCommodityPlantPart(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeCommodityPlantPart(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveCommodityPlantPart(id) {
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
