import { AgriAgrochemicalInstructions } from './../model/AgriAgrochemicalInstructions';
import { environment } from '../../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, retry } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { PageAgriStressControlRecommendation } from '../../agri/models/PageAgriStressControlRecommendation';
import { ResponseMessage } from '../../agri/models/ResponseMessage';
import { PageAgriAgrochemicalInstructions } from '../model/PageAgriAgrochemicalInstructions';

@Injectable({
  providedIn: 'root'
})
export class AgriAgrochemicalInstructionService {

  // Base url
  baseUrl = environment.apiUrl + '/agri/agrochemical-instructions';

  maxSize: number = 10;

constructor(private http: HttpClient) { }

// Http Headers
httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
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

 // GET
 getAgrochemicalInstructionsList(): Observable<AgriAgrochemicalInstructions> {
  return this.http.get<AgriAgrochemicalInstructions>(this.baseUrl + '/list')
  .pipe(
    retry(1),
    catchError(this.errorHandl)
  )
}

//GET
getPageAgrochemicalInstructionsList(page: number, rowSize: number, searchText): Observable<PageAgriAgrochemicalInstructions> {
  this.maxSize = rowSize || this.maxSize;
  let url = this.baseUrl + '/paginatedList' + '?page=' + page + '&size=' + this.maxSize + '&searchText=' + searchText;
  return this.http.get<PageAgriAgrochemicalInstructions>(url)
      .pipe(
          map(response => {
              const data = response;
              return data;
          }));
}

 // Approve
 approveAgrochemicalInstructions(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/primary-approve/' + id , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

 //  Finalize
 finalizeAgrochemicalInstructions(id) {
  return this.http.put<ResponseMessage>(this.baseUrl + '/final-approve/'  + id , this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

  // Reject
  rejectAgrochemicalInstructions(id) {
    return this.http.put<ResponseMessage>(this.baseUrl + '/reject/'  + id , this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.errorHandl)
      )
  }

  //GET
getAgrochemicalInstructionsById(id): Observable<AgriAgrochemicalInstructions> {
  return this.http.get<AgriAgrochemicalInstructions>(this.baseUrl + '/id' + '/' + id)
      .pipe(
          catchError(this.errorHandl)
      );
}

// POST
addAgrochemicalInstructions(data): Observable<ResponseMessage> {

  // console.log("Inside add opp service");

  return this.http.post<ResponseMessage>(this.baseUrl + '/add', JSON.stringify(data), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.errorHandl)
    )
}

//PUT
updateAgrochemicalInstructions(id, data): Observable<ResponseMessage> {
  return this.http.put<ResponseMessage>(this.baseUrl + '/update/' + id, JSON.stringify(data), this.httpOptions)
      .pipe(
          retry(1),
          catchError(this.errorHandl)
      );
}

}
