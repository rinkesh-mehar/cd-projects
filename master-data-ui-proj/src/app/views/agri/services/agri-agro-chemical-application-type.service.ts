import { Injectable } from '@angular/core';

import { environment } from '../../../../environments/environment';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable,throwError, pipe } from 'rxjs';
import { ResponseMessage } from '../../geo/models/ResponseMessage';
import { retry, catchError, map } from 'rxjs/operators';
import { AgriAgroChemicalApplictionType } from '../models/AgriAgroChemicalApllictionType';
import { PageAgriAgrochemicalType } from '../models/PageAgriAgrochemicalType';

@Injectable({
  providedIn: 'root'
})
export class AgriAgroChemicalApplicationTypeService {

baseUrl = environment.apiUrl+'/agri/agrochemical-application-type';
maxSize: number = 10;

  constructor(private http: HttpClient) { }

   // Http Headers
   httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  //Post
  CreateAgroChemicalApplicationType(data):Observable<ResponseMessage>{
    return this.http.post<ResponseMessage>(this.baseUrl+'/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }
  //Get
  GetAgroChemicalApplictionType(id):Observable<AgriAgroChemicalApplictionType>{
    return this.http.get<AgriAgroChemicalApplictionType>(this.baseUrl+'/'+id)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
  }
//Get All
GetAllAgroChemicalApplictionType():Observable<AgriAgroChemicalApplictionType>{
  return this.http.get<AgriAgroChemicalApplictionType>(this.baseUrl+'/list')
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

//Put
UpdateAgroChemicalApplicationType(id,data):Observable<ResponseMessage>{
  return this.http.put<ResponseMessage>(this.baseUrl + '/' +id+ '/update',JSON.stringify(data),this.httpOptions)
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

//Delete
DeleteAgroChemicalApplictionType(id):Observable<ResponseMessage>{
  return this.http.delete<ResponseMessage>(this.baseUrl + '/' +id+' /delete',this.httpOptions)
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}


  // Reject
  RejectAgroChemicalApplictionType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/reject', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //  Finalize
  FinalizeAgroChemicalApplictionType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/final-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }


  // Approve
  ApproveAgroChemicalApplictionType(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + "/" + id + '/primary-approve', this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

 //For-Pagination
 getPageAgroChemicalApplictionType(page: number, rowSize: number,searchText): Observable<PageAgriAgrochemicalType> {
  this.maxSize = rowSize || this.maxSize;
  var url = this.baseUrl + "/paginatedList?page=" + page + "&size=" + this.maxSize  + "&searchText=" + searchText;
  return this.http.get<PageAgriAgrochemicalType>(url)
    .pipe(
      map(response => {
        const data = response;
        console.log(data.content);
        return data;
      }));
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
